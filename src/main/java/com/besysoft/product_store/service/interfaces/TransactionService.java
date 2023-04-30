package com.besysoft.product_store.service.interfaces;

import com.besysoft.product_store.domain.Transaction;
import com.besysoft.product_store.exception.IdNotFoundException;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    Transaction findById(Long id);

    Transaction create(Transaction transaction);

    Transaction update(Long id, Transaction transaction) throws IdNotFoundException;

    void delete(Long id) throws IdNotFoundException;
}
