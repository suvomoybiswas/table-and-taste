package com.tableandtaste.backend.repository;

import com.tableandtaste.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    public List<OrderItem> findAllByOrderId(Long orderId);

    public List<OrderItem> findAllByOrderIdAndQuantityGreaterThan(
            Long orderId,
            Integer minimumQuantity
    );

    Optional<OrderItem> findByOrderIdAndMenuItemId(Long orderId, Long menuItemId);
}