package com.orderms.backend.dto.response;

import com.orderms.backend.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, String customerName, Order.Status status, BigDecimal total, LocalDateTime createdAt, List<OrderItemResponse> items) {}
