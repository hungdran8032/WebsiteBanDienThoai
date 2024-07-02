package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.Product;
import com.project.WebsiteBanDienThoai.service.BrandService;
import com.project.WebsiteBanDienThoai.service.CategoryService;
import com.project.WebsiteBanDienThoai.service.ProductService;
import com.project.WebsiteBanDienThoai.service.TypeOfProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private TypeOfProductService typeOfProductService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    @GetMapping("/admin/products")
    public String showListProduct(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);

        if (productPage.getTotalPages() == 0) {
            model.addAttribute("productPage", productPage);
            return "/admin/products/product-list";
        }

        if (page >= productPage.getTotalPages()) {
            page = productPage.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            productPage = productService.getAllProducts(pageable);
        }

        model.addAttribute("productPage", productPage);
        return "/admin/products/product-list";
    }


    @GetMapping("/products")
    public String showProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "/products/list-product";
    }

    @GetMapping("/products/{id}")
    public String detailProduct(@PathVariable Long id, Model model) {
        Product product = productService.detailProduct(id);
        model.addAttribute("products", product);
        return "products/detail-product";
    }

    @GetMapping("/admin/products/add")
    public String showAddProduct(Model model){
        model.addAttribute("products", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("typeOfProducts", typeOfProductService.getAllTypeOfProducts());
        return "/admin/products/add-product";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@Valid @ModelAttribute("products") Product product, BindingResult result, Model model, @RequestParam("images") MultipartFile images){
        if (result.hasErrors()) {
            return "/admin/products/add-product";
        }
        if (!images.isEmpty()) {
            try {
                String fileName = images.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, images.getBytes());
                product.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi tải lên hình ảnh");
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("brands", brandService.getAllBrands());
                model.addAttribute("typeOfProducts", typeOfProductService.getAllTypeOfProducts());
                return "/admin/products/add-product";
            }
        }
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String showEditProduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.findProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("brands", brandService.getAllBrands());
            model.addAttribute("typeOfProducts", typeOfProductService.getAllTypeOfProducts());
            return "/admin/products/edit-product";
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product, BindingResult result, Model model, @RequestParam("images") MultipartFile images) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("brands", brandService.getAllBrands());
            model.addAttribute("typeOfProducts", typeOfProductService.getAllTypeOfProducts());
            return "/admin/products/edit-product";
        }
        Product existingProduct = productService.findProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (!images.isEmpty()) {
            try {
                String fileName = images.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, images.getBytes());
                product.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi tải lên hình ảnh");
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("brands", brandService.getAllBrands());
                model.addAttribute("typeOfProducts", typeOfProductService.getAllTypeOfProducts());
                return "/admin/products/edit-product";
            }

        } else {
            product.setImage(existingProduct.getImage());
        }
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

}
