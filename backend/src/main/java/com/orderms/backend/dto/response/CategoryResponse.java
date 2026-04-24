package com.orderms.backend.dto.response;

import com.orderms.backend.model.Category;

public record CategoryResponse(Long id, String name) {
    public static CategoryResponse from(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
    }
}
