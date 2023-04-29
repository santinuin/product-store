package com.besysoft.product_store.business.dto;

import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.domain.Seller;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class SaleDto implements Serializable {

    private Long id;

    private Product product;

    private Seller seller;

    @NotNull(message = "Este campo no puede estar vacío")
    private Integer quantity;

    private BigDecimal commission;

    private LocalDateTime createAt;

    public SaleDto() {
    }

    public SaleDto(Long id, Product product, Seller seller, Integer quantity, BigDecimal commission, LocalDateTime createAt) {
        this.id = id;
        this.product = product;
        this.seller = seller;
        this.quantity = quantity;
        this.commission = commission;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDto saleDto = (SaleDto) o;
        return Objects.equals(id, saleDto.id) && Objects.equals(product, saleDto.product) && Objects.equals(seller, saleDto.seller) && Objects.equals(quantity, saleDto.quantity) && Objects.equals(commission, saleDto.commission) && Objects.equals(createAt, saleDto.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, seller, quantity, commission, createAt);
    }
}
