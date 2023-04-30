package com.besysoft.product_store.repository;

import com.besysoft.product_store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCase(String name);

    Optional<Product> findByNameContainingIgnoreCase(String name);

    Optional<Product> findByCategory(String category);
}
