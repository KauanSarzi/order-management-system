package com.orderms.backend.services;

import java.util.List;

import com.orderms.backend.dto.request.CategoryRequest;
import com.orderms.backend.model.Category;
import com.orderms.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return repository.save(category);
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = findById(id);
        category.setName(request.name());
        return repository.save(category);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}