package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.CategoryEnum;
import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.repository.ProductRepository;
import com.besysoft.product_store.service.interfaces.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
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
        when(repository.findByNameContainingIgnoreCase("Raqueta")).thenReturn(Optional.ofNullable(products.get(0)));

        Product product = service.findByName("Raqueta");

        verify(repository, times(1)).findByNameContainingIgnoreCase(anyString());
        assertNotNull(product);
    }

    @Test
    void findByCategory() {
        when(repository.findByCategory(CategoryEnum.DEPORTES)).thenReturn(Optional.of(List.of(products.get(0))));

        List<Product> product = service.findByCategory(CategoryEnum.DEPORTES);

        verify(repository, times(1)).findByCategory(any());
        assertNotNull(product);
    }

    @Test
    void create() throws NameAlreadyExistsException {
        Product newProduct = new Product(3L, "El señor de los anillos", new BigDecimal("8000.00"),
                CategoryEnum.LIBROS);
        when(repository.save(newProduct))
                .thenReturn(newProduct);

        Product product = service.create(newProduct);

        verify(repository, times(1)).save(any(Product.class));
        assertEquals(product, newProduct);
    }

    @Test
    void update() throws NameAlreadyExistsException, IdNotFoundException {
        Product updatedProduct = new Product(2L, "Monitor 24", new BigDecimal("95000.00"), CategoryEnum.COMPUTACION);
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(products.get(1)));
        when(repository.save(any())).thenReturn(updatedProduct);

        Product update = service.update(2L, updatedProduct);

        verify(repository, times(1)).save(any(Product.class));
        assertEquals(updatedProduct, update);
    }

    @Test
    void delete() throws IdNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }
}