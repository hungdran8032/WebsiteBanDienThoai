package com.project.WebsiteBanDienThoai.controller;


import com.project.WebsiteBanDienThoai.config.Config;
import com.project.WebsiteBanDienThoai.model.*;
import com.project.WebsiteBanDienThoai.service.CartService;
import com.project.WebsiteBanDienThoai.service.OrderService;
import com.project.WebsiteBanDienThoai.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @GetMapping("/order/checkout")
    public String checkout(Model model, RedirectAttributes redirectAttributes ) {
        List<CartItem> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi thanh toán.");
            return "redirect:/cart";
        }
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        session.setAttribute("totalPrice", (long)totalPrice);

        model.addAttribute("cartItems", cartItems);
//        model.addAttribute("totalPrice", totalPrice);
        return "/cart/checkout";
    }
    @PostMapping("/order/submit")
    public String submitOrder(@ModelAttribute Order order, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }
        Order newOrder = orderService.createOrder(user, order.getFirstName(),order.getLastName(), order.getAddress(), order.getPhone(),
                order.getDescription(), cartItems);
        Long totalPrice = (Long) session.getAttribute("totalPrice");

        if (totalPrice == null){
            return "redirect:/cart";
        }
        try{
            String url = getPay(totalPrice);
            return "redirect:" + url;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "redirect:/order/confirmation";
        }
    }

    public String getPay(long tien) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = tien*100;
        String bankCode = "NCB";

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    @GetMapping("/order/confirmation")
    public String orderConfirmation(Model model, @ModelAttribute("order") Order order) {
        model.addAttribute("message", "Your order has been successfully placed.");
        model.addAttribute("order", order);
        return "cart/order-confirmation";
    }
    @GetMapping("/order/history")
    public String orderHistory(Model model, Principal principal ) {
        String username = principal.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<OrderDetail> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "/cart/history-order";
    }
    @GetMapping("/admin/qldonhang")
    public String qldonhang(Model model) {
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "/admin/cart/qldonhang";
    }

    @GetMapping("/admin/qldonhang/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        List<StatusOrder> statusOrders = orderService.getAllStatusOrders();
        model.addAttribute("statusOrders", statusOrders);
        model.addAttribute("order", order);
        return "/admin/cart/detail";
    }

    @PostMapping("/admin/qldonhang/{orderId}/update-status")
    public String updateOrderStatus(@PathVariable Long orderId,
                                    @RequestParam Long statusId) {
        orderService.updateOrderStatus(orderId, statusId);
        return "redirect:/admin/qldonhang";
    }
}