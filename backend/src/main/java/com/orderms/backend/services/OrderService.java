package com.orderms.backend.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.orderms.backend.dto.request.OrderItemRequest;
import com.orderms.backend.dto.request.OrderRequest;
import com.orderms.backend.dto.request.OrderStatusRequest;
import com.orderms.backend.model.Customer;
import com.orderms.backend.model.Order;
import com.orderms.backend.model.OrderItem;
import com.orderms.backend.model.Product;
import com.orderms.backend.repositories.CustomerRepository;
import com.orderms.backend.repositories.OrderRepository;
import com.orderms.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(@NonNull Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }


    @SuppressWarnings("null")
    @Transactional
    public Order create(OrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(Order.Status.PENDING);
        order.setItems(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(product.getPrice());
            item.setSubtotal(subtotal);

            order.getItems().add(item);
            total = total.add(subtotal);
        }

        order.setTotal(total);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateStatus(@NonNull Long id, OrderStatusRequest request) {
        Order order = findById(id);
        order.setStatus(request.status());
        return orderRepository.save(order);
    }

    public void delete(@NonNull Long id) {
        orderRepository.deleteById(id);
    }
}