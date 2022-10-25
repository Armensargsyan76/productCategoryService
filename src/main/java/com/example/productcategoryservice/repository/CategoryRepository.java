package com.example.productcategoryservice.repository;

import com.example.productcategoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
