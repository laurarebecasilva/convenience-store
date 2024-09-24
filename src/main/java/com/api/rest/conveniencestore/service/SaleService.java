package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.SaleDto;
import com.api.rest.conveniencestore.dto.SaleListingDto;
import com.api.rest.conveniencestore.enums.PaymentMethod;
import com.api.rest.conveniencestore.model.Product;
import com.api.rest.conveniencestore.model.Sale;
import com.api.rest.conveniencestore.repository.ProductRepository;
import com.api.rest.conveniencestore.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired  //permite que o Spring injetar uma instância do ProductRepository
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate; //facilta a  escritaa e executa comandos SQL (como inserir, atualizar, excluir e consultar dados)

    @Transactional
    public Sale registerSale(SaleDto saleDto) {
        List<Product> products = productRepository.findAllById(saleDto.productIds()); //busca a lista de produtos
        double totalValue = products.stream().mapToDouble(Product::getPrice).sum(); //soma o valor de compra dos produtos
        //lista - pega o preço do produto - soma todos os produtos
        StringBuilder descriptionProduct = new StringBuilder(); // O StringBuilder serve para concatenar as descrições dos produtos
        for (Product product : products) {
            descriptionProduct.append(product.getName()).append(", ");
        }
        Sale sale = new Sale(saleDto, totalValue, descriptionProduct.toString(), products.size()); //salva da venda
        Sale savedSale = saleRepository.save(sale);
        //serve para executaroperações no banco de dados
        for (Long productId : saleDto.productIds()) { //salva os relacionamentos na tabela sales_products
            jdbcTemplate.update("INSERT INTO sales_products (sale_id, product_id) VALUES (?, ?)", savedSale.getId(), productId);
        }
        return savedSale;
    }
    /*
Esse loop percorre o ID de produto da lista productIds do saleDto.
 Dentro do loop, o método insere um registro na
 tabela sales_products, que é uma tabela de junção
 entre vendas e produtos. O comando SQL
 faz o inserte do relacionamento entre a venda (com sale_id) e cada produto vendido (com product_id)
     */

    //lista o produto filtrando pelo metodo de pagamento
    public List<SaleListingDto> listSalesByPaymentMethod(PaymentMethod payment) {
        return saleRepository.findByPaymentMethod(payment)
                .stream()
                .map(SaleListingDto::new)
                .collect(Collectors.toList());
    }
}
