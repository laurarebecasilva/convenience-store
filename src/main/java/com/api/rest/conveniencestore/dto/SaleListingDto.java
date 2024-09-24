package com.api.rest.conveniencestore.dto;

import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.model.Sale;

import java.time.LocalDateTime;

public record SaleListingDto(
        Long id,
        LocalDateTime dateSale,
        double totalValue,
        PaymentMethod paymentMethod,
        int quantity,
        String description
) {
    public SaleListingDto(Sale sale) {//construtor
        this(
                sale.getId(),
                sale.getDateSale(),
                sale.getTotalValue(),
                sale.getPaymentMethod(),
                sale.getQuantity(),
                sale.getDescription()
        );
    }
}


//cria uma instância de SaleListingDto a partir da Sale.