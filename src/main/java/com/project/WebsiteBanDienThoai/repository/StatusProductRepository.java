package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.StatusProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusProductRepository extends JpaRepository<StatusProduct, Long> {
}
