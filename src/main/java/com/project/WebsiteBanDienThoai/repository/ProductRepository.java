package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
