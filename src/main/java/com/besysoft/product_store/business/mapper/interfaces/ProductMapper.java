package com.besysoft.product_store.business.mapper.interfaces;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.domain.Product;

public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    Product partialUpdate(ProductDto productDto, Product product);
}
