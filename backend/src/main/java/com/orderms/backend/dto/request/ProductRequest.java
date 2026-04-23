package com.orderms.backend.dto.request;

import java.math.BigDecimal;

public record ProductRequest(String name, BigDecimal price, Integer stockQuantity, Long categoryId) {}
