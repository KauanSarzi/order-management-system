package com.orderms.backend.controllers;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.orderms.backend.model.Order;
import com.orderms.backend.dto.request.OrderRequest;
import com.orderms.backend.dto.request.OrderStatusRequest;
import com.orderms.backend.services.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable @NonNull Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Order create(@RequestBody OrderRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable @NonNull Long id, @RequestBody OrderStatusRequest request) {
        return service.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NonNull Long id) {
        service.delete(id);
    }
}
