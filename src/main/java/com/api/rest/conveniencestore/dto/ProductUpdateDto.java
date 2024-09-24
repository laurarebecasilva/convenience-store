package com.api.rest.conveniencestore.dto;

import java.time.LocalDate;

public record ProductUpdateDto(

        Double price,

        Integer stockQuantity,

        LocalDate expirationDate) {
}
