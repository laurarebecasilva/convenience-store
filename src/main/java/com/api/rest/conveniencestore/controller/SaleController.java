package com.api.rest.conveniencestore.controller;

import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.dto.SaleListingDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.model.Sale;
import com.api.rest.conveniencestore.service.SaleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    @Transactional
    public ResponseEntity<Sale> register(@Valid @RequestBody SaleDto saleDto) {
        Sale savedSale = saleService.registerSale(saleDto);
        return ResponseEntity.status(201).body(savedSale);
    }

    @GetMapping
    public ResponseEntity<List<SaleListingDto>> listSalesByPaymentMethod(@Valid @RequestParam String paymentMethod) {
        PaymentMethod payment = PaymentMethod.valueOf(paymentMethod.toUpperCase());
        List<SaleListingDto> sales = saleService.listSalesByPaymentMethod(payment);
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

