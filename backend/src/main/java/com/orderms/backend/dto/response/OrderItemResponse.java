package com.orderms.backend.dto.response;

import com.orderms.backend.model.OrderItem;
import java.math.BigDecimal;

public record OrderItemResponse(Long id, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(
            item.getId(),
            item.getProduct().getName(),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getSubtotal()
        );
    }
}
