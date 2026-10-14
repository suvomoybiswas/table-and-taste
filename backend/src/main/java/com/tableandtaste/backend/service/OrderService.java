package com.tableandtaste.backend.service;

import com.tableandtaste.backend.MenuItemNotFoundException;
import com.tableandtaste.backend.entity.MenuItem;
import com.tableandtaste.backend.entity.Order;
import com.tableandtaste.backend.entity.OrderItem;
import com.tableandtaste.backend.exception.OrderNotFoundException;
import com.tableandtaste.backend.repository.MenuItemRepository;
import com.tableandtaste.backend.repository.OrderItemRepository;
import com.tableandtaste.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository, MenuItemRepository menuItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    public Optional<Order> findOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }
    public void removeOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    public OrderItem addOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    public Optional<OrderItem> getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }
    public void removeOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
    public List<OrderItem> getAllOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
    public List<OrderItem> getAllOrderItems(Long orderId, Integer minimumQuantity) {
        return orderItemRepository.findAllByOrderIdAndQuantityGreaterThan(orderId, minimumQuantity);
    }
    public BigDecimal calculateOrderTotal(Long orderId) {
        return orderRepository.calculateOrderTotal(orderId);
    }
    @Transactional
    public Order getUpdatedOrder(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);

        Order order = foundOrder.orElseThrow(
                () -> new OrderNotFoundException(
                        "Order " + orderId + " was not found"
                )
        );

        order.setOrderTotal(
                calculateOrderTotal(orderId)
        );

        return orderRepository.save(order);
    }

    @Transactional
    public Order addOrderItem(
            Long orderId,
            Long menuItemId,
            Integer quantity) {

        // 1. Validate quantity
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (quantity > 10) {
            throw new RuntimeException("Order exceeds quantity limit");
        }

        // 2. Find the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order " + orderId + " was not found"));

        // 3. Make sure the order is still accepting items
        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("Order is not pending");
        }

        // 4. Find the menu item
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item " + menuItemId + " was not found"));

        // 5. Check whether this item already exists in the order
        Optional<OrderItem> orderItem =
                orderItemRepository.findByOrderIdAndMenuItemId(
                        orderId,
                        menuItemId);

        // 6. Existing item
        if (orderItem.isPresent()) {

            if ((orderItem.get().getQuantity() + quantity) > 10) {
                throw new RuntimeException(
                        "Order exceeds quantity limit");
            }

            orderItem.get().setQuantity(
                    orderItem.get().getQuantity() + quantity);

            orderItemRepository.save(orderItem.get());

            // 7. New item
        } else {

            OrderItem newOrderItem = new OrderItem();

            newOrderItem.setOrderId(orderId);
            newOrderItem.setMenuItemId(menuItemId);
            newOrderItem.setQuantity(quantity);

            // Price comes from the database, not the frontend
            newOrderItem.setUnitPrice(menuItem.getPrice());

            // PostgreSQL generates orderItemId
            orderItemRepository.save(newOrderItem);
        }

        // 8. Recalculate the authoritative order total
        order.setOrderTotal(
                orderRepository.calculateOrderTotal(orderId));

        // 9. Save and return the updated order
        return orderRepository.save(order);
    }

}