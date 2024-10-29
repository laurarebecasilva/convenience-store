package com.api.rest.conveniencestore.controller;

import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    // esse endpoint atualiza o status de todos os produtos de acordo com a data de validade
    @PutMapping("/update-status/{productId}")
    public ResponseEntity<String> updateProductStatus(@PathVariable Long productId) {
        stockService.updateProductStatusBasedOnExpiration();
        return ResponseEntity.ok("Product status updated successfully.");
    }

    // Endpoint para verificar o status dos produtos com base na data de validade
    @GetMapping("/check-expiration-products")
    public ResponseEntity<List<Product>> checkExpiredProducts() {
        List<Product> expiredProducts = stockService.getAllExpiredProducts();
        return ResponseEntity.ok(expiredProducts);
    }


    // Endpoint para verificar produtos com baixo estoque limitado a 10 itens
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> checkLowStock() {
        List<Product> lowStockProducts = stockService.getLowStockProducts();
        return ResponseEntity.ok(lowStockProducts);
    }

    // Endpoint para gerar relatorio da baixa de produtos no estoque.
    @PostMapping("/generate-report")
    public ResponseEntity<String> generateReport() {
        stockService.generateStockReport();
        return ResponseEntity.ok("Relatório gerado com sucesso.");
    }
}