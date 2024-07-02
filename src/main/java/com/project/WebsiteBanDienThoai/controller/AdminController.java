package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.User;
import com.project.WebsiteBanDienThoai.service.OrderService;
import com.project.WebsiteBanDienThoai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private OrderService orderService;
    private final UserService userService;
    @GetMapping
    public String index(Model model){
        model.addAttribute("detail", orderService.getAllOrderDetail());
        return "/admin/index";
    }
    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        String username = principal.getName();
        try{
            User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Người dùng này không tồn tại"));
            model.addAttribute("user", user);
        }catch (UsernameNotFoundException e){
            model.addAttribute("errorMessage", "Không tìm thấy");
        }
        return "/admin/profile";
    }
}
