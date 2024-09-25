package com.api.rest.conveniencestore.model;

import com.api.rest.conveniencestore.dto.ProductDto;
import com.api.rest.conveniencestore.dto.ProductUpdateDto;
import com.api.rest.conveniencestore.enums.Category;
import com.api.rest.conveniencestore.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements StatusUtil{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stockQuantity;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Product(ProductDto data) { //construtor
        this.name = data.name();
        this.category = data.category();
        this.price = data.price();
        this.stockQuantity = data.stockQuantity();
        this.expirationDate = data.expirationDate();
        this.status = Status.REGISTERED;
    }

    //atualiza os valores dos campos apos validar se o campo esta nulo
    public void productUpdateData(ProductUpdateDto updateDto) {
        if (updateDto.price() != null) {
            this.price = updateDto.price();
        }
        if (updateDto.stockQuantity() != null) {
            this.stockQuantity = updateDto.stockQuantity();
        }
        if (updateDto.expirationDate() != null) {
            this.expirationDate = updateDto.expirationDate();
        }
    }

    public void setStatus(Status status) { //setter status
        this.status = status;
    }
}
