package com.orderms.backend.services;

import java.util.List;
import com.orderms.backend.dto.request.CategoryRequest;
import com.orderms.backend.dto.response.CategoryResponse;
import com.orderms.backend.model.Category;
import com.orderms.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryResponse> findAll() {
        return repository.findAll().stream().map(CategoryResponse::from).toList();
    }

    public CategoryResponse findById(@NonNull Long id) {
        return CategoryResponse.from(findEntityById(id));
    }

    public Category findEntityById(@NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return CategoryResponse.from(repository.save(category));
    }

    public CategoryResponse update(@NonNull Long id, CategoryRequest request) {
        Category category = findEntityById(id);
        category.setName(request.name());
        return CategoryResponse.from(repository.save(category));
    }

    public void delete(@NonNull Long id) {
        repository.deleteById(id);
    }
}
