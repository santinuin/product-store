package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.business.mapper.interfaces.ProductMapper;
import com.besysoft.product_store.domain.CategoryEnum;
import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.service.interfaces.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static com.besysoft.product_store.data.LoadData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ProductService service;

    @MockBean
    ProductMapper productMapper;

    ObjectMapper mapper;
    List<Product> productList;
    List<ProductDto> productDtoList;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();

        productList = List.of(crearProducto1().orElseThrow(),
                crearProducto2().orElseThrow());

        productDtoList = List.of(crearProductoDto1().orElseThrow(),
                crearProductoDto2().orElseThrow());
    }

    @Test
    void list() throws Exception {
        when(service.findAll()).thenReturn(productList);
        when(productMapper.toDto(productList.get(0))).thenReturn(productDtoList.get(0));
        when(productMapper.toDto(productList.get(1))).thenReturn(productDtoList.get(1));

        mvc.perform(get("/productos").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(content().json(mapper.writeValueAsString(productList)));
        verify(service).findAll();
        verify(productMapper, times(2)).toDto(any(Product.class));
    }

    @Test
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(productList.get(0));
        when(productMapper.toDto(productList.get(0))).thenReturn(productDtoList.get(0));

        mvc.perform(get("/productos/buscar").contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productDtoList.get(0))));
        verify(service).findById(anyLong());
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void findByCategory() throws Exception {
        when(service.findByCategory(CategoryEnum.DEPORTES)).thenReturn(List.of(productList.get(0)));
        when(productMapper.toDto(productList.get(0))).thenReturn(productDtoList.get(0));

        mvc.perform(get("/productos/{categoria}", "DEPORTES")
                .contentType(MediaType.APPLICATION_JSON))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(List.of(productDtoList.get(0)))));
        verify(service).findByCategory(any());
        verify(productMapper, times(1)).toDto(any(Product.class));

    }

    @Test
    void create() throws Exception {
        Product product = new Product(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES);
        ProductDto productDto = new ProductDto(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES);
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(service.create(product)).thenReturn(product);

        mvc.perform(post("/productos/crear").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ha sido creado con éxito"))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.product.name").value("Raqueta"));
        verify(service).create(any());
        verify(productMapper).toEntity(any());
    }

    @Test
    void update() throws Exception {
        Long id = 1L;
        ProductDto updatedProductDto = new ProductDto(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES);
        Product updatedProduct = new Product(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES);
        when(productMapper.toEntity(updatedProductDto)).thenReturn(updatedProduct);
        when(service.update(1L, updatedProduct)).thenReturn(updatedProduct);

        mvc.perform(put("/productos/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedProductDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ID: " + id + " ha sido modificado con éxito"));
        verify(service).update(anyLong(), any());
        verify(productMapper).toEntity(any());
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;
        mvc.perform(delete("/productos/eliminar/{id}", id).contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ID: " + id + " ha sido eliminado con éxito"));

        verify(service).delete(anyLong());
    }
}