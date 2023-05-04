package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.business.dto.TransactionDto;
import com.besysoft.product_store.business.mapper.interfaces.TransactionMapper;
import com.besysoft.product_store.data.LoadData;
import com.besysoft.product_store.domain.*;
import com.besysoft.product_store.service.interfaces.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.besysoft.product_store.data.LoadData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransactionService service;

    @MockBean
    TransactionMapper transactionMapper;

    ObjectMapper mapper;
    List<Transaction> transactionList;
    List<TransactionDto> transactionDtoList;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        transactionList = List.of(crearTransaction1().orElseThrow());

        transactionDtoList = List.of(crearTransactionDto1().orElseThrow());
    }

    @Test
    void list() throws Exception {
        when(service.findAll()).thenReturn(transactionList);
        when(transactionMapper.toDto(transactionList.get(0))).thenReturn(transactionDtoList.get(0));

        mvc.perform(get("/ventas").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
        verify(service).findAll();
        verify(transactionMapper, times(1)).toDto(any(Transaction.class));

    }

    @Test
    void create() throws Exception {
        Transaction transaction = new Transaction(1L, List.of(new TransactionDetail(1L,
                new Product(1L, "Raqueta", new BigDecimal("70000.00"),
                        CategoryEnum.DEPORTES), 1, null)),
                new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)),
                LocalDateTime.now(), null, null);
        TransactionDto transactionDto = new TransactionDto(1L, List.of(new TransactionDetail(1L,
                new Product(1L, "Raqueta", new BigDecimal("70000.00"),
                        CategoryEnum.DEPORTES), 1, null)),
                new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)),
                LocalDateTime.now(), null, null);

        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(service.create(transaction)).thenReturn(transaction);

        mvc.perform(post("/ventas/crear").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transactionDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Venta creada con éxito"));
        verify(service).create(any());
        verify(transactionMapper).toEntity(any());
    }
}