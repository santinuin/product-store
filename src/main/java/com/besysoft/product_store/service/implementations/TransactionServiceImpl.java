package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.Transaction;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.repository.ProductRepository;
import com.besysoft.product_store.repository.TransactionRepository;
import com.besysoft.product_store.service.interfaces.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final ProductRepository productRepository;

    public TransactionServiceImpl(TransactionRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Transaction create(Transaction transaction) {

        transaction.getTransactionsDetail()
                        .forEach(x -> {
                            x.setProduct(productRepository.findById(x.getProduct().getId()).orElseThrow());
                            x.generateSubTotal();
                        });

        transaction.setCreateAt(LocalDateTime.now());

        return this.repository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction update(Long id, Transaction transaction) throws IdNotFoundException {

        Optional<Transaction> transactionToUpdate = this.repository.findById(id);

        if(transactionToUpdate.isEmpty()){
            throw new IdNotFoundException("Error: no se pudo editar, la venta ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        transactionToUpdate.get().setSeller(transaction.getSeller());
        transactionToUpdate.get().setTransactionsDetail(transaction.getTransactionsDetail());

        return this.repository.save(transactionToUpdate.orElseThrow());
    }

    @Override
    @Transactional
    public void delete(Long id) throws IdNotFoundException {

        Optional<Transaction> transactionToDelete = this.repository.findById(id);

        if(transactionToDelete.isEmpty()){
            throw new IdNotFoundException("Error: no se pudo eliminar, la venta ID: "
                    .concat(id.toString().concat(" no existe.")));
        }


        this.repository.deleteById(id);

    }
}
