package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.repository.ProductRepository;
import com.besysoft.product_store.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) throws IdNotFoundException {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findByName(String name) {
        return this.repository.findByNameContainingIgnoreCase(name).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findByCategory(String category) {
        return this.repository.findByCategory(category).orElseThrow();
    }

    @Override
    @Transactional
    public Product create(Product product) throws NameAlreadyExistsException {

        Optional<Product> productByName = this.repository.findByNameIgnoreCase(product.getName());

        if (productByName.isPresent()) {
            throw new NameAlreadyExistsException("Error: no se pudo crear, el nombre: "
                    .concat(product.getName().concat(" ya existe.")));
        }

        return this.repository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) throws IdNotFoundException, NameAlreadyExistsException {

        Optional<Product> productToUpdate = this.repository.findById(id);

        if (productToUpdate.isEmpty()) {
            throw new IdNotFoundException("Error: no se pudo editar, el producto ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        Optional<Product> productByName = this.repository.findByNameIgnoreCase(product.getName());

        if (productByName.isPresent()
                && !Objects.equals(productByName.get().getName(), productToUpdate.get().getName())) {
            throw new NameAlreadyExistsException("Error: no se pudo editar, el nombre "
                    .concat(product.getName().concat(" ya existe.")));
        }

        productToUpdate.get().setName(product.getName());
        productToUpdate.get().setPrice(product.getPrice());
        productToUpdate.get().setCategory(product.getCategory());

        return this.repository.save(productToUpdate.get());
    }

    @Override
    @Transactional
    public void delete(Long id) throws IdNotFoundException {

        Optional<Product> productToDelete = this.repository.findById(id);

        if (productToDelete.isEmpty()) {
            throw new IdNotFoundException("Error: no se pudo eliminar, el producto ID: "
                    .concat(id.toString().concat(" no existe.")));
        }
    }
}
