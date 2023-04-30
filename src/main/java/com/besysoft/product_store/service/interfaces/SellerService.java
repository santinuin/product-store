package com.besysoft.product_store.service.interfaces;

import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;

import java.util.List;

public interface SellerService {

    List<Seller> findAll();

    Seller findById(Long id);

    Seller findByName(String name);

    Seller create(Seller seller) throws NameAlreadyExistsException;

    Seller update(Long id, Seller seller) throws IdNotFoundException, NameAlreadyExistsException;

    void delete(Long id) throws IdNotFoundException;
}
