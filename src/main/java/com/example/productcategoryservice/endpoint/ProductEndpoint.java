package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        if (productService.getAllProducts().size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.map(productService.getAllProducts()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") int id) {
        Optional<Product> byOptional = productService.getProductById(id);
        if (byOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.map(byOptional.get()));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.save(productRequestDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable int id, @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.map(productRequestDto);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        if (productService.isUpdateProduct(productRequestDto)) {
            return ResponseEntity.ok(productMapper.map(productService.updateProduct(product, id)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {
        Optional<Product> byOptional = productService.getProductById(id);
        if (byOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (productService.deleteProductById(byOptional.get())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/by/category/{id}")
    public ResponseEntity<List<ProductResponseDto>> getProductByCategoryId(@PathVariable("id") int id) {
        return ResponseEntity.ok(productMapper.map(productService.getProductByCategoryId(id)));
    }

}
