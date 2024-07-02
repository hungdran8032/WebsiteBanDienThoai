package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTypeOfProductId(Long typeOfProduct);

    List<Product> findByNameContaining(String keyword);

}