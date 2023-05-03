package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.domain.Transaction;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.repository.SellerRepository;
import com.besysoft.product_store.repository.TransactionRepository;
import com.besysoft.product_store.service.interfaces.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.besysoft.product_store.data.LoadData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SellerServiceImplTest {

    @MockBean
    SellerRepository repository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    SellerService service;

    List<Seller> sellers;


    @BeforeEach
    void setUp() {

        sellers = List.of(crearSeller1().orElseThrow(),
                crearSeller2().orElseThrow());

    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(sellers);

        List<Seller> sellerList = service.findAll();

        verify(repository, times(1)).findAll();
        assertFalse(sellerList.isEmpty());
        assertEquals(2, sellerList.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(sellers.get(0)));

        Seller seller = service.findById(1L);

        verify(repository, times(1)).findById(anyLong());
        assertNotNull(seller);
    }

    @Test
    void findByName() {
        when(repository.findByNameContainingIgnoreCase("Santiago")).thenReturn(Optional.ofNullable(sellers.get(0)));

        Seller seller = service.findByName("Santiago");

        verify(repository, times(1)).findByNameContainingIgnoreCase(anyString());
        assertNotNull(seller);
    }

    @Test
    void commissionBySeller() throws IdNotFoundException {
        Transaction transaction = crearTransaction1().orElseThrow();
        transaction.setSellCommission(new BigDecimal("3500.00"));
        when(repository.findById(1L)).thenReturn(crearSeller1());
        when(transactionRepository.findBySellerId(1L)).thenReturn(List.of(transaction));

        BigDecimal commission = service.commissionBySeller(1L);

        assertEquals(new BigDecimal("3500.00"), commission);
        verify(repository).findById(anyLong());
        verify(transactionRepository).findBySellerId(anyLong());
    }

    @Test
    void create() throws NameAlreadyExistsException {
        Seller newSeller = new Seller(null, "Santiago Nuñez", new BigDecimal(120000));
        when(repository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(repository.save(any())).then(invocation -> {
            Seller s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        Seller seller = service.create(newSeller);

        verify(repository, times(1)).save(any(Seller.class));
        verify(repository, times(1)).findByNameIgnoreCase(any());
        assertEquals(1L, seller.getId());
        assertEquals("Santiago Nuñez", seller.getName());
    }

    @Test
    void update() throws NameAlreadyExistsException, IdNotFoundException {
        Seller update = new Seller(null, "Santiago Nuñez", new BigDecimal(150000));
        when(repository.findById(1L)).thenReturn(crearSeller1());
        when(repository.save(any())).thenReturn(update);

        Seller updatedTransaction = service.update(1L, update);

        verify(repository).save(any(Seller.class));
        assertEquals(update, updatedTransaction);
    }

    @Test
    void delete() throws IdNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(sellers.get(0)));

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }
}