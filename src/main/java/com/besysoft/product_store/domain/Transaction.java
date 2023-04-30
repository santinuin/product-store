package com.besysoft.product_store.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private List<TransactionDetail> transactionsDetail;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public Transaction() {
    }

    public Transaction(Long id, List<TransactionDetail> transactionsDetail, Seller seller, LocalDateTime createAt) {
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionsDetail, that.transactionsDetail) && Objects.equals(seller, that.seller) && Objects.equals(createAt, that.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionsDetail, seller, createAt);
    }
}
