package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
