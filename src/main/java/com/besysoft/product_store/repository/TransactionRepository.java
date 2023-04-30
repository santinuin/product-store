package com.besysoft.product_store.repository;

import com.besysoft.product_store.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SUM(t.sellCommission)"
            + "FROM Transaction AS t"
            + "WHERE t.seller.id == :sellerId",
            nativeQuery = true)
    BigDecimal calculateComisionSeller(Long sellerId);
}
