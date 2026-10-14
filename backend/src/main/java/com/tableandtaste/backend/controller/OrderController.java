package com.tableandtaste.backend.controller;

import com.tableandtaste.backend.dto.AddOrderItemRequest;
import com.tableandtaste.backend.entity.Order;
import com.tableandtaste.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/{id}/total")
    public Order getUpdatedOrder(@PathVariable Long id) {
        return orderService.getUpdatedOrder(id);
    }
    @PostMapping("/{orderId}")
    public Order postOrder(
            @PathVariable Long orderId,
            @RequestBody AddOrderItemRequest request) {

        return orderService.addOrderItem(
                orderId,
                request.getMenuItemId(),
                request.getQuantity()
        );
    }
}
