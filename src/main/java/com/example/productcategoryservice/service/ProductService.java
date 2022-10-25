package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> updateProduct(Product product) {
        if (product == null) {
            return Optional.empty();
        }
        productRepository.save(product);
        return Optional.of(product);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductByCategoryId(int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isEmpty()) {
            throw new RuntimeException();
        }
        List<Product> all = productRepository.findAll();
        List<Product> byCategory = new ArrayList<>();
        for (Product product : all) {
            if (product.getCategory().getId() == id) {
                byCategory.add(product);
            }
        }
        return byCategory;
    }
}
