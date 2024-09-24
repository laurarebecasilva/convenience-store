package com.api.rest.conveniencestore.dto;

import com.api.rest.conveniencestore.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProductDto(
        @NotBlank
        String name,

        @NotBlank
        Category category,

        @NotNull
        double price,

        @NotNull
        int stockQuantity,

        LocalDate expirationDate) {
}
