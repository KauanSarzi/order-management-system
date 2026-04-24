package com.orderms.backend.controllers;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.orderms.backend.model.Customer;
import com.orderms.backend.dto.request.CustomerRequest;
import com.orderms.backend.services.CustomerService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public List<Customer> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable @NonNull Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Customer create(@RequestBody CustomerRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable @NonNull Long id, @RequestBody CustomerRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NonNull Long id) {
        service.delete(id);
    }
}