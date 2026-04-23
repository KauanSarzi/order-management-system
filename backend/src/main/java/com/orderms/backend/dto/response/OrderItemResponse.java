package com.orderms.backend.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {}
