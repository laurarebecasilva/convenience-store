package com.api.rest.conveniencestore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
        FUEL,
        FOOD,
        BEVERAGES,
        CLEANING_PRODUCTS;

        @JsonCreator
        public static Category fromValue(String value) { //converte uma string que vem do json para uma enum
                return Category.valueOf(value.toUpperCase());
        }
}
