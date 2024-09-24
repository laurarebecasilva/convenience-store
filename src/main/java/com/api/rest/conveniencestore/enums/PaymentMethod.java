package com.api.rest.conveniencestore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethod {
    CASH,
    CREDIT,
    DEBIT;

    @JsonCreator
    public static PaymentMethod fromValue(String value) { //converte uma string que vem do json para uma enum
        return PaymentMethod.valueOf(value.toUpperCase());
    }
}

