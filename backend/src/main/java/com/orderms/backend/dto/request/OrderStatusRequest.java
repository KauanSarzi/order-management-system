package com.orderms.backend.dto.request;

import com.orderms.backend.model.Order;
import jakarta.validation.constraints.NotNull;

public record OrderStatusRequest(@NotNull Order.Status status) {}
