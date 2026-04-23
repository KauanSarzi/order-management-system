package com.orderms.backend.dto.request;

import com.orderms.backend.model.Order;

public record OrderStatusRequest(Order.Status status) {}
