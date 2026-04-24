package com.orderms.backend.services;

import java.util.List;
import com.orderms.backend.dto.request.ProductRequest;
import com.orderms.backend.model.Category;
import com.orderms.backend.model.Product;
import com.orderms.backend.repositories.CategoryRepository;
import com.orderms.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(@NonNull Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @SuppressWarnings("null") // tirar caso de problema
    public Product create(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @SuppressWarnings("null")
    public Product update(@NonNull Long id, ProductRequest request) {
        Product product = findById(id);
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void delete(@NonNull Long id) {
        productRepository.deleteById(id);
    }
}