package com.project.WebsiteBanDienThoai.service;

import com.project.WebsiteBanDienThoai.model.*;
import com.project.WebsiteBanDienThoai.repository.OrderDetailRepository;
import com.project.WebsiteBanDienThoai.repository.OrderRepository;
import com.project.WebsiteBanDienThoai.repository.StatusOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private StatusOrderRepository statusOrderRepository;
    @Autowired
    private ProductService productService;
    @Transactional
    public Order createOrder(User user, String fistName, String lastName, String address, String phone, String description, List<CartItem> cartItems) {
        Order order = new Order();
        order.setFirstName(fistName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setPhone(phone);
        order.setDescription(description);
        order.setTotalPrice(0.0);
        order.setStatusOrder(statusOrderRepository.findById(1L).orElseThrow(() -> new RuntimeException("StatusOrder not found")));
        order.setOrderDate(new Date());
        order = orderRepository.save(order);

        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setUser(user);
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);

            totalPrice += item.getProduct().getPrice() * item.getQuantity();
            productService.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        cartService.clearCart();
        return order;
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }
    public List<OrderDetail> getOrdersByUser(User user) {
        return orderDetailRepository.findByUser(user);
    }
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public List<StatusOrder> getAllStatusOrders() {
        return statusOrderRepository.findAll();
    }
    @Transactional
    public void updateOrderStatus(Long orderId, Long statusOrderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Optional<StatusOrder> optionalStatusOrder = statusOrderRepository.findById(statusOrderId);
            if (optionalStatusOrder.isPresent()) {
                StatusOrder statusOrder = optionalStatusOrder.get();
                order.setStatusOrder(statusOrder);
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Status order not found with id: " + statusOrderId);
            }
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }
}