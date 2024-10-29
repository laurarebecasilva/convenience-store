package com.api.rest.conveniencestore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE,
    INACTIVE,
    REGISTERED,
    APPROVED,
    CANCELLED,
    EXPIRED,
    NEAR_EXPIRATION,
    AVAILABLE,
    LOW_STOCK;

    @JsonCreator
    public static Status fromValueStatus(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
