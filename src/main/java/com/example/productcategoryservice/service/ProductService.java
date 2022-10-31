package com.example.productcategoryservice.service;

import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.repository.ProductRepository;
import com.example.productcategoryservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    private final UserMapper userMapper;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product save(ProductRequestDto productRequestDto) {
        User user = userMapper.map(productRequestDto.getUserProductDetailDto());
        Product product = productMapper.map(productRequestDto);
        product.setUser(user);
        productRepository.save(product);
        return product;
    }

    public boolean isUpdateProduct(ProductRequestDto productRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        if (productRequestDto.getUserProductDetailDto().getId() == user.getId()) {
            return true;
        }
        return false;
    }

    private boolean isDeleteProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        if (product.getUser().getId() == user.getId()) {
            return true;
        }
        return false;
    }

    public Product updateProduct(Product product, int id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Product productFromDB = byId.get();
        productFromDB.builder()
                .id(product.getId())
                .title(product.getTitle())
                .count(product.getCount())
                .price(product.getPrice())
                .category(product.getCategory())
                .user(product.getUser())
                .build();
        productRepository.save(productFromDB);
        return productFromDB;
    }

    public boolean deleteProductById(Product product) {
        if (isDeleteProduct(product)) {
            productRepository.deleteById(product.getId());
            return true;
        }
        return false;
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
