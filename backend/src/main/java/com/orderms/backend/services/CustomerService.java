package com.orderms.backend.services;

import java.util.List;
import com.orderms.backend.model.Customer;
import com.orderms.backend.dto.request.CustomerRequest;
import com.orderms.backend.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

// CustomerService
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(@NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer create(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        return repository.save(customer);
    }

    public Customer update(@NonNull Long id, CustomerRequest request) {
        Customer customer = findById(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        return repository.save(customer);
    }

    public void delete(@NonNull Long id) {
        repository.deleteById(id);
    }
}