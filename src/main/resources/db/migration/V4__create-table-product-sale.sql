CREATE TABLE sales_products (
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (sale_id, product_id),
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
