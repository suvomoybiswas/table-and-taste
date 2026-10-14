package com.tableandtaste.backend.repository;

import com.tableandtaste.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT calculate_order_total(:orderId)")
    public BigDecimal calculateOrderTotal(
            @Param("orderId") Long orderId
    );
}