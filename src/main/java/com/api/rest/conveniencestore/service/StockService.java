package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.repository.ProductRepository;
import com.api.rest.conveniencestore.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.rest.conveniencestore.utils.CsvReportGenerator;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DateUtil dateUtil;

    @Transactional
    public void updateProductStatusBasedOnExpiration() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            product.updateStatusBasedOnExpiration();
            productRepository.save(product);
        }
    }

    public List<Product> getAllExpiredProducts() {
        return productRepository.findByStatus(Status.EXPIRED);
    }

    // Retorna produtos com baixo estoque - quantidade minima 10 itens
    public List<Product> getLowStockProducts() {
        int minStockThreshold = 10;
        return productRepository.findByStockQuantityLessThan(minStockThreshold);
    }

    public void generateStockReport() {
        List<Product> lowStockProducts = getLowStockProducts();
        List<Product> expiredProducts = getAllExpiredProducts();

        if (lowStockProducts.isEmpty() && expiredProducts.isEmpty()) {
            System.out.println("Nenhum produto com baixa de estoque ou próximo ao vencimento. Relatório não gerado.");
            return;
        }

        // Configura os cabeçalhos
        String[] headers = {"ID", "Name_product", "Quantity", "Expiration_date", "Status"};
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(lowStockProducts);
        allProducts.addAll(expiredProducts);
        //mapeia os produtos para linhas CSV
        CsvReportGenerator.generateCsvReport(
                "low_stock_expiration_report",
                headers,
                allProducts,
                product -> String.format("%d,%s,%d,%s,%s",
                        product.getId(),
                        product.getName(),
                        product.getStockQuantity(),
                        product.getExpirationDate(),
                        product.getStatus() == Status.EXPIRED ? Status.EXPIRED : Status.LOW_STOCK
                )
        );
    }
}

