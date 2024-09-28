package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.ProductDto;
import com.api.rest.conveniencestore.dto.ProductListingDto;
import com.api.rest.conveniencestore.dto.ProductUpdateDto;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired // permite que o Spring injete uma instância do ProductRepository
    private ProductRepository productRepository;

    @Transactional
    public Product registerProduct(ProductDto productDto) {
        Product product = new Product(productDto);
        return productRepository.save(product);
    }

    public List<ProductListingDto> listProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductListingDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Product updateProduct(Long id, ProductUpdateDto updateDto) {
        Product product = productRepository.getReferenceById(id);
        product.productUpdateData(updateDto);
        return productRepository.save(product);
    }

    //atraves deste metodo, eu consigo inativar o produto ativo, sem precisar exclui-lo.
    @Transactional
    public Product statusProductInactive(Long id, Status status) {
        Product product = productRepository.getReferenceById(id);
        product.setStatus(status.INACTIVE);
        return productRepository.save(product);
    }
}

