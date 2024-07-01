package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findByUser(User user);
    Optional<Order> findById(Long id);
}
