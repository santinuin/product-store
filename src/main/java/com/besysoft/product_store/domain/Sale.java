package com.besysoft.product_store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(nullable = false)
    @NotNull
    private Integer quantity;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal commission;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public Sale() {
    }

    public Sale(Long id, Product product, Seller seller, Integer quantity, BigDecimal commission, LocalDateTime createAt) {
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
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id)
                && Objects.equals(product, sale.product)
                && Objects.equals(seller, sale.seller)
                && Objects.equals(quantity, sale.quantity)
                && Objects.equals(commission, sale.commission)
                && Objects.equals(createAt, sale.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, seller, quantity, commission, createAt);
    }
}
