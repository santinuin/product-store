package com.besysoft.product_store.business.dto;

import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.domain.TransactionDetail;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TransactionDto implements Serializable {

    private Long id;

    @NotNull(message = "Este campo no puede estar vacío")
    private List<TransactionDetail> transactionsDetail;

    private Seller seller;

    private LocalDateTime createAt;

    public TransactionDto() {
    }

    public TransactionDto(Long id, List<TransactionDetail> transactionsDetail, Seller seller, LocalDateTime createAt) {
        this.id = id;
        this.transactionsDetail = transactionsDetail;
        this.seller = seller;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TransactionDetail> getTransactionsDetail() {
        return transactionsDetail;
    }

    public void setTransactionsDetail(List<TransactionDetail> transactionsDetail) {
        this.transactionsDetail = transactionsDetail;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionsDetail, that.transactionsDetail) && Objects.equals(seller, that.seller) && Objects.equals(createAt, that.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionsDetail, seller, createAt);
    }
}
