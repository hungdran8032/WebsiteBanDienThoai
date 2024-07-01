package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("find5ProductsType1", productService.get5ProductsOfType(1L));
        model.addAttribute("find5ProductsType2", productService.get5ProductsOfType(2L));
        return "home";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

}
