package com.oms.service;

import com.oms.entity.Order;
import com.oms.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(@NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order not found: " + id));
    }

    public Order create(@NonNull Order order) {
        order.setId(null);
        return repository.save(order);
    }

    public Order update(@NonNull Long id, @NonNull Order patch) {
        Order existing = findById(id);

        if (patch.getCustomerName() != null) existing.setCustomerName(patch.getCustomerName());
        if (patch.getProduct()      != null) existing.setProduct(patch.getProduct());
        if (patch.getQuantity()     != null) existing.setQuantity(patch.getQuantity());
        if (patch.getStatus()       != null) existing.setStatus(patch.getStatus());
        if (patch.getTotal()        != null) existing.setTotal(patch.getTotal());

        return repository.save(existing);
    }

    public void deleteById(@NonNull Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
