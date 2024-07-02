package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.Brand;
import com.project.WebsiteBanDienThoai.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BrandController {
    @Autowired
    private final BrandService brandService;

    // Hiển thị danh sách
    @GetMapping("/brands")
    public String listBrand(Model model) {
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "/admin/brands/list-brand";
    }

    // Thêm brand
    @GetMapping("/brands/add")
    public String formAddBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "/admin/brands/add-brand";
    }

    @PostMapping("/brands/add")
    public String addBrand(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/brands/add-brand";
        }
        brandService.saveBrand(brand);
        return "redirect:/admin/brands";
    }

    //Chỉnh sửa brand
    @GetMapping("/brands/edit/{id}")
    public String formEditBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "/admin/brands/update-brand";
        }
        return "redirect:/admin/brands";
    }

    @PostMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable("id") Long id, @Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/brands/update-brand";
        }
        brandService.updateBrand(id, brand);
        return "redirect:/admin/brands";
    }

    //Xóa brand
    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, Model model) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands";
    }
}
