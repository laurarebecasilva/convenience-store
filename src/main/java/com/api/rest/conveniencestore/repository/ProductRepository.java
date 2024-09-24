package com.api.rest.conveniencestore.repository;

import com.api.rest.conveniencestore.enums.Category;
import com.api.rest.conveniencestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //busca os produtos com base na categoria
    Collection<Product> findByCategory(Category category);
}

