package hr.algreba.valentinherve.productapp.dto;

import hr.algreba.valentinherve.productapp.domain.Product;

import java.math.BigDecimal;

public class ProductDTO {
    private final String name;
    private final BigDecimal price;
    private final long stock;
    private final Long id;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.id = product.getId();
        this.stock = product.getStock();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public long getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
