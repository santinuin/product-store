package com.besysoft.product_store.data;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.business.dto.SellerDto;
import com.besysoft.product_store.business.dto.TransactionDto;
import com.besysoft.product_store.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LoadData {

    /*ENTITIES*/

    //PRODUCTS

    public static Optional<Product> crearProducto1(){
        return Optional.of(new Product(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES));
    }

    public static Optional<Product> crearProducto2(){
        return Optional.of(new Product(2L, "Monitor 24", new BigDecimal("90000.00"), CategoryEnum.COMPUTACION));
    }

    //SELLERS

    public static  Optional<Seller> crearSeller1(){
        return Optional.of(new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)));
    }

    public static  Optional<Seller> crearSeller2(){
        return Optional.of(new Seller(2L, "David Gomez", new BigDecimal(120000)));
    }

    //TRANSACTIONS

    public static Optional<Transaction> crearTransaction1(){
        return Optional.of(new Transaction(1L, List.of(new TransactionDetail(1L,
                new Product(1L, "Raqueta", new BigDecimal("70000.00"),
                        CategoryEnum.DEPORTES), 1, null)),
                new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)),
                LocalDateTime.now(), null, null));
    }

    /*DTO*/

    //PRODUCTS

    public static Optional<ProductDto> crearProductoDto1() {
        return Optional.of(new ProductDto(1L, "Raqueta", new BigDecimal("70000.00"), CategoryEnum.DEPORTES));
    }

    public static Optional<ProductDto> crearProductoDto2(){
        return Optional.of(new ProductDto(2L, "Monitor 24", new BigDecimal("90000.00"), CategoryEnum.COMPUTACION));
    }

    //SELLERS
    public static  Optional<SellerDto> crearSellerDto1(){
        return Optional.of(new SellerDto(1L, "Santiago Nuñez", new BigDecimal(120000), null));
    }

    public static  Optional<SellerDto> crearSellerDto2(){
        return Optional.of(new SellerDto(2L, "David Gomez", new BigDecimal(120000), null));
    }

    //TRANSACTIONS
    public static Optional<TransactionDto> crearTransactionDto1(){
        return Optional.of(new TransactionDto(1L, List.of(new TransactionDetail(1L,
                new Product(1L, "Raqueta", new BigDecimal("70000.00"),
                        CategoryEnum.DEPORTES), 1, null)),
                new Seller(1L, "Santiago Nuñez", new BigDecimal(120000)),
                LocalDateTime.now(), null, null));
    }
}
