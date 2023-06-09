package com.besysoft.product_store.controller;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.business.mapper.interfaces.ProductMapper;
import com.besysoft.product_store.domain.CategoryEnum;
import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.service.interfaces.ProductService;
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
@RequestMapping("/productos")
@Api(value = "Product Controller", tags = "Acciones permitidas para productos")
public class ProductController {

    private final ProductService service;

    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation(value = "Listar",
            notes = "Lista todos los productos")
    public ResponseEntity<?> list() {

        List<ProductDto> productDtoList = this.service.findAll()
                .stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);

    }

    @GetMapping("/buscar")
    @ApiOperation(value = "Buscar productos",
            notes = "Podemos buscar por ID o por Nombre, siempre que se lo especifiquemos en los parametros," +
                    "(siempre va a ponderar ID por sobre el nombre si se especifican ambos)")
    public ResponseEntity<?> findByIdOrName(@RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String name) throws IdNotFoundException {
        if (id != null) {
            ProductDto productDto = this.mapper.toDto(this.service.findById(id));

            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }

        ProductDto productDto = this.mapper.toDto(this.service.findByName(name));

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/{categoria}")
    @ApiOperation(value = "Buscar por categoria")
    public ResponseEntity<?> findByCategory(@PathVariable CategoryEnum categoria) {

        List<ProductDto> productDtoList = this.service.findByCategory(categoria)
                .stream()
                .map(this.mapper::toDto)
                .toList();

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ApiOperation(value = "Crear producto nuevo")
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto) throws NameAlreadyExistsException {

        Map<String, Object> response = new HashMap<>();

        Product newProduct = this.service.create(this.mapper.toEntity(productDto));

        response.put("message", "El producto ha sido creado con éxito");
        response.put("product", newProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Modificar producto")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody ProductDto productDto) throws IdNotFoundException, NameAlreadyExistsException {

        Map<String, Object> response = new HashMap<>();

        Product updatedProduct = this.service.update(id, this.mapper.toEntity(productDto));

        response.put("message", "El producto ID: " + updatedProduct.getId() + " ha sido modificado con éxito");
        response.put("product", updatedProduct);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @ApiOperation(value = "Eliminar producto")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        this.service.delete(id);

        response.put("message", "El producto ID: " + id + " ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
