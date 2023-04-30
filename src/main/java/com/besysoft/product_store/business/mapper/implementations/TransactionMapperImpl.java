package com.besysoft.product_store.business.mapper.implementations;

import com.besysoft.product_store.business.dto.TransactionDto;
import com.besysoft.product_store.business.mapper.interfaces.TransactionMapper;
import com.besysoft.product_store.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        if(transactionDto == null){
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId(transactionDto.getId());
        transaction.setTransactionsDetail(transactionDto.getTransactionsDetail());
        transaction.setSeller(transactionDto.getSeller());
        transaction.setSellCommission(transactionDto.getSellCommission());
        transaction.setTotal(transactionDto.getTotal());

        return transaction;
    }

    @Override
    public TransactionDto toDto(Transaction transaction) {
        if(transaction == null){
            return null;
        }

        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setId(transaction.getId());
        transactionDto.setTransactionsDetail(transaction.getTransactionsDetail());
        transactionDto.setSeller(transaction.getSeller());
        transactionDto.setCreateAt(transaction.getCreateAt());
        transactionDto.setSellCommission(transaction.getSellCommission());
        transactionDto.setTotal(transaction.getTotal());

        return transactionDto;
    }

    @Override
    public Transaction partialUpdate(TransactionDto transactionDto, Transaction transaction) {
        if (transactionDto == null) {
            return transaction;
        }

        if (transactionDto.getTransactionsDetail() != null) {
            transaction.setTransactionsDetail(transactionDto.getTransactionsDetail());
        }
        if (transactionDto.getSeller() != null) {
            transaction.setSeller(transactionDto.getSeller());
        }
        if (transactionDto.getSellCommission() != null) {
            transaction.setSellCommission(transactionDto.getSellCommission());
        }
        if (transactionDto.getTotal() != null) {
            transaction.setTotal(transactionDto.getTotal());
        }


        return transaction;
    }
}
