package com.orderms.backend.dto.response;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, Integer stockQuantity, String categoryName) {}
