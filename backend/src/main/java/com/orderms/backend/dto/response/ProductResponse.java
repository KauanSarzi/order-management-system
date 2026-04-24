package com.orderms.backend.dto.response;

import com.orderms.backend.model.Product;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, Integer stockQuantity, String categoryName) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getStockQuantity(), p.getCategory().getName());
    }
}
