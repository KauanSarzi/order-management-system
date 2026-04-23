package com.orderms.backend.dto.request;

import java.util.List;

public record OrderRequest(Long customerId, List<OrderItemRequest> items) {}
