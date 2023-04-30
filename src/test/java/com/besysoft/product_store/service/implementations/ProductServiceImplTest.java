package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.data.LoadData;
import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.repository.ProductRepository;
import com.besysoft.product_store.service.interfaces.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.besysoft.product_store.data.LoadData.crearProducto1;
import static com.besysoft.product_store.data.LoadData.crearProducto2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    ProductRepository repository;

    @Autowired
    ProductService service;

    List<Product> products;

    @BeforeEach
    void setUp() {

        products = List.of(crearProducto1().orElseThrow(),
                crearProducto2().orElseThrow());

    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(products);

        List<Product> productList = service.findAll();

        verify(repository, times(1)).findAll();
        assertFalse(productList.isEmpty());
        assertEquals(2, productList.size());
    }

    @Test
    void findById() throws IdNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));

        Product product = service.findById(1L);

        verify(repository, times(1)).findById(anyLong());
        assertNotNull(product);
    }

    @Test
    void findByName() {
    }

    @Test
    void findByCategory() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}