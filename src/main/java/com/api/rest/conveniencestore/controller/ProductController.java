package com.api.rest.conveniencestore.controller;


import com.api.rest.conveniencestore.dto.ProductDto;
import com.api.rest.conveniencestore.dto.ProductListingDto;
import com.api.rest.conveniencestore.dto.ProductUpdateDto;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<Product> register(@Valid @RequestBody ProductDto productDto) {
        Product savedProduct = productService.registerProduct(productDto);
        return ResponseEntity.status(200).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductListingDto>> list() {
        var products = productService.listProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto updateDto) {
        Product updatedProduct = productService.updateProduct(id, updateDto);
        return ResponseEntity.status(200).body(updatedProduct);
    }
}

