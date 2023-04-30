package com.besysoft.product_store.service.interfaces;

import com.besysoft.product_store.domain.CategoryEnum;
import com.besysoft.product_store.domain.Product;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id) throws IdNotFoundException;

    Product findByName(String name);

    List<Product> findByCategory(CategoryEnum category);

    Product create(Product product) throws NameAlreadyExistsException;

    Product update(Long id, Product product) throws IdNotFoundException, NameAlreadyExistsException;

    void delete(Long id) throws IdNotFoundException;
}
