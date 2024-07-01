package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.TypeOfProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfProductRepository extends JpaRepository<TypeOfProduct, Long> {
}