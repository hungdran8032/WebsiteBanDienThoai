package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
