package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.*;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.repository.ProductRepository;
import com.besysoft.product_store.repository.TransactionRepository;
import com.besysoft.product_store.service.interfaces.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.besysoft.product_store.data.LoadData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceImplTest {

    @MockBean
    TransactionRepository repository;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    TransactionService service;

    List<Product> products;

    List<Seller> sellers;

    List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        products = List.of(crearProducto1().orElseThrow(),
                crearProducto2().orElseThrow());

        sellers = List.of(crearSeller1().orElseThrow(),
                crearSeller2().orElseThrow());

        transactions = List.of(crearTransaction1().orElseThrow());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(transactions);

        List<Transaction> allTransactions = service.findAll();

        verify(repository).findAll();
        assertFalse(allTransactions.isEmpty());
        assertEquals(1, allTransactions.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(crearTransaction1());

        Transaction transactionById =  service.findById(1L);

        verify(repository).findById(anyLong());
        assertNotNull(transactionById);
    }

    @Test
    void create() {
        List<TransactionDetail> newDetails = List.of(new TransactionDetail(1L, products.get(0),
                1, null));
        Transaction newTransaction = new Transaction(null, newDetails, sellers.get(0),
                null, null, BigDecimal.valueOf(0));
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(products.get(0)));
        when(repository.save(any())).then(invocation -> {
            Transaction t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Transaction transactionCreated = service.create(newTransaction);

        assertEquals(1L, transactionCreated.getId());
        assertEquals("70000.00", transactionCreated.getTotal().toPlainString());
        assertEquals("3500.0000", transactionCreated.getSellCommission().toPlainString());
        assertEquals(LocalDateTime.now().getHour(), transactionCreated.getCreateAt().getHour());
        assertEquals("70000.00",
                transactionCreated.getTransactionsDetail().get(0).getSubTotal().toPlainString());

        verify(productRepository).findById(any());
        verify(repository).save(any());
    }

    @Test
    void update() throws IdNotFoundException {
        Transaction update = new Transaction(1L, List.of(new TransactionDetail(1L,
                new Product(1L, "Raqueta", new BigDecimal("50000.00"),
                        CategoryEnum.DEPORTES), 3, null)),
                new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)),
                LocalDateTime.now(), null, null);
        when(repository.findById(1L)).thenReturn(crearTransaction1());
        when(repository.save(any())).thenReturn(update);

        Transaction updatedTransaction = service.update(1L, update);

        verify(repository).save(any(Transaction.class));
        assertEquals(update, updatedTransaction);
    }

    @Test
    void delete() throws IdNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(transactions.get(0)));

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }
}