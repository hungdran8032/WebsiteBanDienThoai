package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}