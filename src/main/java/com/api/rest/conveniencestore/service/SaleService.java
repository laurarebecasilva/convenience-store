package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.ClientDto;
import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.dto.SaleListingDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.exceptions.*;
import com.api.rest.conveniencestore.model.Client;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.model.Sale;
import com.api.rest.conveniencestore.repository.ClientRepository;
import com.api.rest.conveniencestore.repository.ProductRepository;
import com.api.rest.conveniencestore.repository.SaleRepository;
import com.api.rest.conveniencestore.utils.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DateUtil dateUtil;

    @Transactional
    public Sale registerSale(SaleDto saleDto, Client client) throws ProductNotFoundException, ProductInactiveException, ProductInsufficientStockException, SaleNotValidPaymentMethodException, ClientCpfNotFoundException {
        // Calcula o valor total da venda e cria a descrição dos produtos
        double totalValue = calculateTotalValue(saleDto);
        String description = generateSaleDescription(saleDto, client);
        int totalQuantity = saleDto.quantity().stream().mapToInt(Integer::intValue).sum(); // Soma total de quantidades

        LocalDateTime saleDate = LocalDateTime.now();
        DateUtil.formatDate(saleDate); // Formata a data da venda usando a classe utilitária DateUtil


        // Cria a venda com os detalhes, passando a data formatada
        Sale sale = new Sale(saleDto, totalValue, description, totalQuantity, saleDate);

        validatePaymentMethod(sale, sale.getPaymentMethod());

        // Atualiza o estoque dos produtos
        for (int i = 0; i < saleDto.productIds().size(); i++) {
            Long productId = saleDto.productIds().get(i);
            Integer quantity = saleDto.quantity().get(i);

            Product product;
            product = validationProduct(productId, quantity); //chama as validações

            // Subtrai a quantidade vendida do estoque
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product); // Atualiza o estoque do produto
        }
/*
        // Verifica se o cliente existe no repositório pelo CPF
        if (!clientRepository.existsByCpf(client.getCpf())) {
            throw new ClientCpfNotFoundException("Cpf não encontrado: " + client.getCpf());
        }
 */
        // Salva a venda e retorna o objeto Sale com as informações
        return saleRepository.save(sale);
    }


    public List<SaleListingDto> listSalesByPaymentMethod(PaymentMethod payment) {
        return saleRepository.findByPaymentMethod(payment)
                .stream()
                .map(SaleListingDto::new)
                .collect(Collectors.toList());
    }

    /*
    @Transactional
    public Sale statusSale(Long id, Status status) {
        Sale sale = saleRepository.getReferenceById(id);
        if (status != null) {
            sale.setStatus(status.CANCELLED);
        }

        return saleRepository.save(sale);
    }
    */


    // Método para calcular o valor total da venda
    private double calculateTotalValue(SaleDto saleDto) throws ProductNotFoundException {
        double total = 0;
        for (int i = 0; i < saleDto.productIds().size(); i++) {
            Long productId = saleDto.productIds().get(i);
            Integer quantity = saleDto.quantity().get(i);
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado: " + productId));
            total += product.getPrice() * quantity;
        }
        return total;
    }


    // Método para gerar uma descrição da venda
    private String generateSaleDescription(SaleDto saleDto, Client client) throws ProductNotFoundException {
        StringBuilder description = new StringBuilder();


        description.append("CPF: ").append(client.getCpf()).append(" Produtos: ");

        for (int i = 0; i < saleDto.productIds().size(); i++) {
            Long productId = saleDto.productIds().get(i);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado: " + productId));
            description.append("cod")
                    .append(saleDto.productIds().get(i)) // quantidade
                    .append(" ")
                    .append(product.getName())
                    .append(" ")
                    .append(saleDto.quantity())
                    .append("x - R$")
                    .append(String.format("%.2f ", product.getPrice()))
                    .append(" ");
        }
        return description.toString(); // Retorna a descrição formatada após o loop
    }

    private Product validationProduct(Long productId, int quantity) throws ProductNotFoundException, ProductInactiveException, ProductInsufficientStockException {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Produto não encontrado: " + productId);
        }
        // Se o produto existe, busca ele pelo findbyid
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado: " + productId));

        if (product.getStatus().equals(Status.INACTIVE)) {
            throw new ProductInactiveException("Produto inativo: " + product.getName());
        }

        if (product.getStockQuantity() < quantity) { // Verifica se o produto está ativo e se há estoque suficiente
            throw new ProductInsufficientStockException("Estoque insuficiente para o produto: " + product.getName() + " " + product.getStockQuantity() + ", unidades em estoque.");
        }
        return product;
    }

    private void validatePaymentMethod(Sale sale, PaymentMethod paymentMethod) throws SaleNotValidPaymentMethodException {
        if (paymentMethod == null) {
            throw new SaleNotValidPaymentMethodException("Método de pagamento não pode ser vazio");
        }

        if (!sale.getPaymentMethod().equals(paymentMethod)) {
            throw new SaleNotValidPaymentMethodException("Método de pagamento não válido: " + paymentMethod);
        }
    }
}














    /*
Esse loop percorre o ID de produto da lista productIds do saleDto.
 Dentro do loop, o método insere um registro na
 tabela sales_products, que é uma tabela de junção
 entre vendas e produtos. O comando SQL
 faz o inserte do relacionamento entre a venda (com sale_id) e cada produto vendido (com product_id)
     */

    //lista o produto filtrando pelo metodo de pagamento


