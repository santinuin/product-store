package com.besysoft.product_store.business.mapper.interfaces;

import com.besysoft.product_store.business.dto.TransactionDto;
import com.besysoft.product_store.domain.Transaction;

public interface TransactionMapper {
    Transaction toEntity(TransactionDto transactionDto);

    TransactionDto toDto(Transaction transaction);

    Transaction partialUpdate(TransactionDto transactionDto, Transaction transaction);
}
