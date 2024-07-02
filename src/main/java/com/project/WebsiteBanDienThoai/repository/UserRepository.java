package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
