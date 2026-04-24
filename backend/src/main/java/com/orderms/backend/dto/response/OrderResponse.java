package com.orderms.backend.dto.response;

import com.orderms.backend.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, Long customerId, String customerName, Order.Status status, BigDecimal total, LocalDateTime createdAt, List<OrderItemResponse> items) {
    public static OrderResponse from(Order o) {
        List<OrderItemResponse> items = o.getItems() == null ? List.of() :
            o.getItems().stream().map(OrderItemResponse::from).toList();
        return new OrderResponse(
            o.getId(),
            o.getCustomer().getId(),
            o.getCustomer().getName(),
            o.getStatus(),
            o.getTotal(),
            o.getCreatedAt(),
            items
        );
    }
}
