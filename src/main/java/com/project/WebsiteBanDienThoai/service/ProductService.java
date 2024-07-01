package com.project.WebsiteBanDienThoai.service;

import com.project.WebsiteBanDienThoai.model.Product;
import com.project.WebsiteBanDienThoai.repository.ProductRepository;
import com.project.WebsiteBanDienThoai.repository.StatusProductRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StatusProductRepository statusProductRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product){
        if(product.getStock()>0){
            product.setStatusProduct(statusProductRepository.findById(1L).orElseThrow(() -> new RuntimeException("StatusProduct not found")));
        }
        else if (product.getStock() == 0){
            product.setStatusProduct(statusProductRepository.findById(2L).orElseThrow(() -> new RuntimeException("StatusProduct not found")));
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(@NotNull Product product){
        Product existProduct = findProductById(product.getId()).orElseThrow(() -> new IllegalStateException("Sản phẩm "+ product.getId() + "không tồn tại."));
        existProduct.setName(product.getName());
        existProduct.setBrand(product.getBrand());
        existProduct.setCategory(product.getCategory());
        existProduct.setDescription(product.getDescription());
        existProduct.setStock(product.getStock());
        existProduct.setImage(product.getImage());
        existProduct.setPrice(product.getPrice());

        if(existProduct.getStock()>0){
            existProduct.setStatusProduct(statusProductRepository.findById(1L).orElseThrow(() -> new RuntimeException("StatusProduct not found")));
        }
        else if (existProduct.getStock() == 0){
            existProduct.setStatusProduct(statusProductRepository.findById(2L).orElseThrow(() -> new RuntimeException("StatusProduct not found")));
        }
        existProduct.setTypeOfProduct(product.getTypeOfProduct());
        return productRepository.save(existProduct);
    }

    @Transactional
    public void updateStock(Long productId, int quantityToReduce) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        int currentStock = product.getStock();
        if (currentStock < quantityToReduce) {
            throw new RuntimeException("Không đủ sản phẩm có id: " + productId);
        }
        product.setStock(currentStock - quantityToReduce);

        if (product.getStock() == 0) {
            product.setStatusProduct(statusProductRepository.findById(2L).orElseThrow(() -> new RuntimeException("StatusProduct not found")));
        }
        productRepository.save(product);
    }
    public Product detailProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> get5ProductsOfType(Long typeOfProductId) {
        List<Product> allProductsOfType = productRepository.findByTypeOfProductId(typeOfProductId);
        return allProductsOfType.subList(0, Math.min(6, allProductsOfType.size()));
    }
}
