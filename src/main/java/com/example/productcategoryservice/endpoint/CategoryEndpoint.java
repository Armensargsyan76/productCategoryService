package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CategoryRequestDto;
import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryEndpoint {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory() {
        if (categoryService.findAll().size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.map(categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.map(byId.get()));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.map(categoryRequestDto);
        categoryService.createCategory(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping()
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Optional<Category> byOptional = categoryService.updateCategory(categoryMapper.map(categoryRequestDto));
        if (byOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(categoryMapper.map(byOptional.get()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

}
