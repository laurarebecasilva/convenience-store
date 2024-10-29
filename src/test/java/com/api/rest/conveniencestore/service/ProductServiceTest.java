package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.ProductDto;
import com.api.rest.conveniencestore.enums.Category;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    ProductDto productDto;

    Product product;

    @Test
    public void testRegisterProduct() {
        productDto = new ProductDto("coxinha", Category.FOOD, 8.00, 3, LocalDate.of(2024, 10, 29));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Product product = productService.registerProduct(productDto);

        assertNotNull(product);
        assertEquals("coxinha", product.getName());
        assertEquals(Category.FOOD, product.getCategory());
        assertEquals(8.00, product.getPrice());
        assertEquals(3, product.getStockQuantity());
        assertEquals(LocalDate.of(2024, 10, 29), product.getExpirationDate());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
