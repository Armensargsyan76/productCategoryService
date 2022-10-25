package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }


    public Optional<Category> updateCategory(Category category) {
        if (category == null){
            return Optional.empty();
        }
        categoryRepository.save(category);
        return Optional.of(category);
    }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
}
