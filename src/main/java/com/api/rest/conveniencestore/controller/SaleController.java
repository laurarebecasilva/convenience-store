package com.api.rest.conveniencestore.controller;

import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.dto.SaleListingDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.exceptions.*;
import com.api.rest.conveniencestore.model.Client;
import com.api.rest.conveniencestore.model.Sale;
import com.api.rest.conveniencestore.repository.SaleRepository;
import com.api.rest.conveniencestore.service.SaleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Sale> register(@Valid @RequestBody SaleDto saleDto, Client client) throws ProductNotFoundException, ProductInactiveException, ProductInsufficientStockException, SaleNotValidPaymentMethodException, ClientCpfNotFoundException {
        Sale savedSale = saleService.registerSale(saleDto, client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
    }

    @GetMapping
    public ResponseEntity<List<SaleListingDto>> listSalesByPaymentMethod(@Valid @RequestParam String paymentMethod) throws SaleListingNullException {
        // Verifica se a string paymentMethod é nula ou está vazia
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new SaleListingNullException("O método de pagamento não pode ser vazio.");
        }

        PaymentMethod payment;
        try {
            // Tenta converter a string para o enum PaymentMethod
            payment = PaymentMethod.valueOf(paymentMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            // se o tipo de pagamento não for válido
            throw new SaleListingNullException("Método de pagamento inválido: " + paymentMethod);
        }
        // Busca as vendas pelo método de pagamento
        List<SaleListingDto> sales = saleService.listSalesByPaymentMethod(payment);
        // Retorna a lista de vendas
        return ResponseEntity.ok(sales);
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Sale> status(@Valid @PathVariable Long id, @RequestBody Map<String, String> statusRequest) {
        String statusString = statusRequest.get("status");
        Status statusCanceled = Status.fromValueStatus(statusString); // Converte a string para enum
        Sale updatedStatusSale = saleService.statusSale(id, statusCanceled);
        return ResponseEntity.ok(updatedStatusSale);
    }
}

