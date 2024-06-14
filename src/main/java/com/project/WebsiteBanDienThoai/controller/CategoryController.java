package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.Category;
import com.project.WebsiteBanDienThoai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Controller

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/categories/list-category";
    }

    @GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "/categories/add-category";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "/categories/edit-category";
        } else {
            return "404";
        }
    }

    @PostMapping("/categories/edit")
    public String editCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}