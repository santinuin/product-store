package com.besysoft.product_store.repository;

import com.besysoft.product_store.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findBySeller_Id(Long id);
}
