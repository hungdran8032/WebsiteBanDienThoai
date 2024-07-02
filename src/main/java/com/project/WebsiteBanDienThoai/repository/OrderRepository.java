package com.project.WebsiteBanDienThoai.repository;

import com.project.WebsiteBanDienThoai.model.MonthlyProductCount;
import com.project.WebsiteBanDienThoai.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findByUser(User user);
    Optional<Order> findById(Long id);
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);

    @Query("SELECT new com.project.WebsiteBanDienThoai.model.MonthlyProductCount(MONTH(o.orderDate), YEAR(o.orderDate), SUM(od.quantity)) " +
            "FROM Order o JOIN o.orderDetails od " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<MonthlyProductCount> findMonthlyProductCount();
}
