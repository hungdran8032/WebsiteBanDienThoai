package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.OrderDetail;
import com.project.WebsiteBanDienThoai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByUser(User user);
}
