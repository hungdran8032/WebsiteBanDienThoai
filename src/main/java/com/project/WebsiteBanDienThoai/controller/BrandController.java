package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.Brand;
import com.project.WebsiteBanDienThoai.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BrandController {
    @Autowired
    private final BrandService brandService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    // Hiển thị danh sách
    @GetMapping("/brands")
    public String listBrand(Model model) {
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "/brands/list-brand";
    }

    // Thêm brand
    @GetMapping("/brands/add")
    public String formAddBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "/brands/add-brand";
    }

    @PostMapping("/brands/add")
    public String addBrand(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model, @RequestParam("images") MultipartFile images) {
        if (result.hasErrors()) {
            return "/brands/add-brand";
        }
        if (!images.isEmpty()) {
            try {
                String fileName = images.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, images.getBytes());
                brand.setLogo(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi tải lên hình ảnh");
                return "/brands/add-brand";
            }
        }
        brandService.saveBrand(brand);
        return "redirect:/brands";
    }

    //Chỉnh sửa brand
    @GetMapping("/brands/edit/{id}")
    public String formEditBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "/brands/update-brand";
        }
        return "redirect:/brands";
    }

    @PostMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable("id") Long id, @Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model, @RequestParam("images") MultipartFile images) {
        if (result.hasErrors()) {
            return "/brands/update-brand";
        }
        if (!images.isEmpty()) {
            try {
                String fileName = images.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, images.getBytes());
                brand.setLogo(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi tải lên hình ảnh");
                return "/brands/update-brand";
            }
        }
        brandService.updateBrand(id, brand);
        return "redirect:/brands";
    }

    //Xóa brand
    @GetMapping("/brands/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "/brands/update-brand";
        }
        brandService.deleteBrand(id);
        model.addAttribute("brands", brandService.getAllBrands());
        return "redirect:/brands";
    }
}
