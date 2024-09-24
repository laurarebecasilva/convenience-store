package com.api.rest.conveniencestore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE,
    INACTIVE;

    @JsonCreator
    public static Status fromValue(String value) { //converte uma string que vem do json para uma enum
        return Status.valueOf(value.toUpperCase());
    }
}
