package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusOrderRepository extends JpaRepository<StatusOrder, Long> {
    Optional<StatusOrder> findById(Long id);
    List<StatusOrder> findAll();
}
