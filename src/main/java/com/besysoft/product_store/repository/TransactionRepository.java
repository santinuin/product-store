package com.besysoft.product_store.repository;

import com.besysoft.product_store.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findBySeller_Id(Long id);
}
