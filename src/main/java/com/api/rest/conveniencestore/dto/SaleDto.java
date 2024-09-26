package com.api.rest.conveniencestore.dto;

import com.api.rest.conveniencestore.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SaleDto(

        @NotEmpty(message = "Product IDs cannot be empty")
        List<Long> productIds,

        PaymentMethod paymentMethod,

        List<String> description) //contem os nomes dos produtos como lista
{
}
