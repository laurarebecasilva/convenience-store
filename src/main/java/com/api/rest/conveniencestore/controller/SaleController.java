package com.api.rest.conveniencestore.controller;

import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.dto.SaleListingDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.model.Sale;
import com.api.rest.conveniencestore.service.SaleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    @Transactional
    public ResponseEntity<Sale> register(@Valid @RequestBody SaleDto saleDto) {
        Sale savedSale = saleService.registerSale(saleDto);
        return ResponseEntity.status(200).body(savedSale);
    }

    @GetMapping
    public ResponseEntity<List<SaleListingDto>> listSalesByPaymentMethod(@RequestParam String paymentMethod) {
        PaymentMethod payment = PaymentMethod.valueOf(paymentMethod.toUpperCase());
        List<SaleListingDto> sales = saleService.listSalesByPaymentMethod(payment);
        return ResponseEntity.ok(sales);
    }
}

