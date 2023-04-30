package com.besysoft.product_store.repository;

import com.besysoft.product_store.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByNameIgnoreCase(String name);

    Optional<Seller> findByNameContainingIgnoreCase(String name);
}
