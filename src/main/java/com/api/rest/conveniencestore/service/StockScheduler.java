package com.api.rest.conveniencestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockScheduler {

    @Autowired
    private StockService stockService;

    @Scheduled(cron = "0 0 0 * * ?") // Executa diariamente à meia-noite
    public void checkProductExpirationDates() {
        stockService.updateProductStatusBasedOnExpiration();
        stockService.generateStockReport(); // Gera o relatório somente se necessário
    }
}
