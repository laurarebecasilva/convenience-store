package com.api.rest.conveniencestore.model;

import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "sales")
@Entity(name = "Sale")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements StatusUtil{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateSale;

    @Column(nullable = false)
    @NotNull(message = "Total Value cannot be null")
    private double totalValue;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    //atualiza os valores dos campos apos validar se o campo esta nulo
    public Sale(SaleDto saleDto, double totalValue, String description, int quantity) {
        this.dateSale = LocalDateTime.now();
        this.totalValue = totalValue;
        this.paymentMethod = saleDto.paymentMethod();
        this.quantity = quantity;
        this.description = description;
        this.status = Status.APPROVED;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
