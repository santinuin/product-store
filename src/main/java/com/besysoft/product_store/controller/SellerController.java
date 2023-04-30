package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.SellerDto;
import com.besysoft.product_store.business.mapper.interfaces.SellerMapper;
import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.service.interfaces.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendedores")
public class SellerController {

    private final SellerService service;

    private final SellerMapper mapper;


    public SellerController(SellerService service, SellerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> list() {

        List<SellerDto> sellerDtoList = this.service.findAll()
                .stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(sellerDtoList, HttpStatus.OK);

    }

    @GetMapping("/buscar")
    public ResponseEntity<?> findByIdOrName(@RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String name) throws IdNotFoundException {
        if (id != null) {
            SellerDto sellerDto = this.mapper.toDto(this.service.findById(id));

            return new ResponseEntity<>(sellerDto, HttpStatus.OK);
        }

        SellerDto sellerDto = this.mapper.toDto(this.service.findByName(name));

        return new ResponseEntity<>(sellerDto, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public ResponseEntity<?> create(@Valid @RequestBody SellerDto sellerDto) throws NameAlreadyExistsException {

        Map<String, Object> response = new HashMap<>();

        Seller newSeller = this.service.create(this.mapper.toEntity(sellerDto));

        response.put("message", "El vendedor ha sido dado de alta");
        response.put("product", newSeller);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody SellerDto sellerDto) throws IdNotFoundException, NameAlreadyExistsException {

        Map<String, Object> response = new HashMap<>();

        Seller updatedSeller = this.service.update(id, this.mapper.toEntity(sellerDto));

        response.put("message", "El vendedor ID: " + updatedSeller.getId() + " ha sido modificado con éxito");
        response.put("product", updatedSeller);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        this.service.delete(id);

        response.put("message", "El vendedor ID: " + id + " ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
