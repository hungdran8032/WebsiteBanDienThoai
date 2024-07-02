package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.OrderDetail;
import com.project.WebsiteBanDienThoai.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByUser(User user);
    List<OrderDetail> findByOrderId(Long orderId);
    Page<OrderDetail> findByUser(User user, Pageable pageable);

    @Query("SELECT od.product, SUM(od.quantity) as totalQuantity FROM OrderDetail od WHERE od.order.orderDate BETWEEN :startDate AND :endDate GROUP BY od.product ORDER BY totalQuantity DESC")
    List<Object[]> findTop3ProductsByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
