package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.TransactionDto;
import com.besysoft.product_store.business.mapper.interfaces.TransactionMapper;
import com.besysoft.product_store.domain.Transaction;
import com.besysoft.product_store.service.interfaces.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ventas")
@Api(value = "Transaction Controller", tags = "Acciones permitidas para la venta")
public class TransactionController {

    private final TransactionService service;

    private final TransactionMapper mapper;


    public TransactionController(TransactionService service, TransactionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation(value = "Listar",
            notes = "Lista todas las ventas")
    public ResponseEntity<?> list() {

        List<TransactionDto> transactionDtos = this.service.findAll()
                .stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(transactionDtos, HttpStatus.OK);

    }
    @PostMapping("/crear")
    @ApiOperation(value = "Generar Venta")
    public ResponseEntity<?> create(@Valid @RequestBody TransactionDto transactionDto) {

        Map<String, Object> response = new HashMap<>();

        Transaction newTransaction = this.service.create(this.mapper.toEntity(transactionDto));

        response.put("message", "Venta creada con éxito");
        response.put("product", newTransaction);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
