package com.besysoft.product_store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions_details")
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @Column(nullable = false)
    @NotNull
    private Integer quantity;

    private BigDecimal subTotal;

    public TransactionDetail() {
    }

    public TransactionDetail(Long id, Product product, Integer quantity, BigDecimal subTotal) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.subTotal = subTotal;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void generateSubTotal(){

        this.subTotal = this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetail that = (TransactionDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(subTotal, that.subTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, subTotal);
    }
}
