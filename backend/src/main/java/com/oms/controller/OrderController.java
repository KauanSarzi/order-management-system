package com.oms.controller;

import com.oms.entity.Order;
import com.oms.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable @NonNull Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody @NonNull Order order) {
        return service.create(order);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable @NonNull Long id, @RequestBody @NonNull Order patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        service.deleteById(id);
    }
}
