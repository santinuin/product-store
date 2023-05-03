package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.SellerDto;
import com.besysoft.product_store.business.mapper.interfaces.SellerMapper;
import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.service.interfaces.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.besysoft.product_store.data.LoadData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SellerController.class)
class SellerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    SellerService service;

    @MockBean
    SellerMapper sellerMapper;

    ObjectMapper mapper;
    List<Seller> sellerList;
    List<SellerDto> sellerDtoList;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();

        sellerList = List.of(crearSeller1().orElseThrow(),
                crearSeller2().orElseThrow());

        sellerDtoList = List.of(crearSellerDto1().orElseThrow(),
                crearSellerDto2().orElseThrow());
    }

    @Test
    void list() throws Exception {
        when(service.findAll()).thenReturn(sellerList);
        when(sellerMapper.toDto(sellerList.get(0))).thenReturn(sellerDtoList.get(0));
        when(sellerMapper.toDto(sellerList.get(1))).thenReturn(sellerDtoList.get(1));

        mvc.perform(get("/vendedores").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(content().json(mapper.writeValueAsString(sellerList)));
        verify(service).findAll();
        verify(sellerMapper, times(2)).toDto(any(Seller.class));

    }

    @Test
    void findByIdOrName() throws Exception {
        when(service.findById(1L)).thenReturn(sellerList.get(0));
        when(sellerMapper.toDto(sellerList.get(0))).thenReturn(sellerDtoList.get(0));

        mvc.perform(get("/vendedores/buscar").contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(sellerDtoList.get(0))));
        verify(service).findById(anyLong());
        verify(sellerMapper, times(1)).toDto(any(Seller.class));
    }

    @Test
    void findSellerWithCommission() throws Exception {
        Long id = 1L;
        when(service.findById(1L)).thenReturn(sellerList.get(0));
        when(sellerMapper.toDto(sellerList.get(0))).thenReturn(sellerDtoList.get(0));

        mvc.perform(get("/vendedores/{id}/comision", id).contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(sellerDtoList.get(0))));
        verify(service).commissionBySeller(anyLong());
        verify(sellerMapper, times(1)).toDto(any(Seller.class));
    }

    @Test
    void create() throws Exception {
        Seller seller = new Seller(1L, "Santiago Nuñez", new BigDecimal(120000));
        SellerDto sellerDto = new SellerDto(1L, "Santiago Nuñez", new BigDecimal(120000), null);
        when(sellerMapper.toEntity(sellerDto)).thenReturn(seller);
        when(service.create(seller)).thenReturn(seller);

        mvc.perform(post("/vendedores/alta").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sellerDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El vendedor ha sido dado de alta"))
                .andExpect(jsonPath("$.seller.id").value(1))
                .andExpect(jsonPath("$.seller.name").value("Santiago Nuñez"));
        verify(service).create(any());
        verify(sellerMapper).toEntity(any());
    }

    @Test
    void update() throws Exception {
        Long id = 1L;
        Seller updatedSeller = new Seller(1L, "Santiago Nuñez", new BigDecimal(120000));
        SellerDto updatedSellerDto = new SellerDto(1L, "Santiago Nuñez", new BigDecimal(120000), null);
        when(sellerMapper.toEntity(updatedSellerDto)).thenReturn(updatedSeller);
        when(service.update(1L, updatedSeller)).thenReturn(updatedSeller);

        mvc.perform(put("/vendedores/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedSellerDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El vendedor ID: " + id + " ha sido modificado con éxito"));
        verify(service).update(anyLong(), any());
        verify(sellerMapper).toEntity(any());
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;
        mvc.perform(delete("/vendedores/eliminar/{id}", id).contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El vendedor ID: " + id + " ha sido eliminado con éxito"));

        verify(service).delete(anyLong());
    }
}