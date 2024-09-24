package com.api.rest.conveniencestore.dto;

import com.api.rest.conveniencestore.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SaleDto(
        @NotNull
        List<Long> productIds,

        @NotBlank
        PaymentMethod paymentMethod,

        List<String> description) //contem os nomes dos produtos como lista
{
}
