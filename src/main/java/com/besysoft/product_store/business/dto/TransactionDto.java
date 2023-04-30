package com.besysoft.product_store.business.dto;

import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.domain.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TransactionDto implements Serializable {

    private Long id;

    @NotNull(message = "Este campo no puede estar vacío")
    private List<TransactionDetail> transactionsDetail;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Seller seller;

    private LocalDateTime createAt;

    private BigDecimal sellCommission;

    private BigDecimal total;

    public TransactionDto() {
    }

    public TransactionDto(Long id, List<TransactionDetail> transactionsDetail,
                          Seller seller, LocalDateTime createAt, BigDecimal sellCommission,
                          BigDecimal total) {
        this.id = id;
        this.transactionsDetail = transactionsDetail;
        this.seller = seller;
        this.createAt = createAt;
        this.sellCommission = sellCommission;
        this.total = total;
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

    public BigDecimal getSellCommission() {
        return sellCommission;
    }

    public void setSellCommission(BigDecimal sellCommission) {
        this.sellCommission = sellCommission;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionsDetail, that.transactionsDetail)
                && Objects.equals(seller, that.seller) && Objects.equals(createAt, that.createAt)
                && Objects.equals(sellCommission, that.sellCommission) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionsDetail, seller, createAt, sellCommission, total);
    }
}
