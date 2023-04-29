package com.besysoft.product_store.business.mapper.implementations;

import com.besysoft.product_store.business.dto.ProductDto;
import com.besysoft.product_store.business.mapper.interfaces.ProductMapper;
import com.besysoft.product_store.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product toEntity(ProductDto productDto) {
        if (productDto == null){
            return null;
        }

        Product product = new Product();

        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        return product;
    }

    @Override
    public ProductDto toDto(Product product) {
        if (product == null){
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());

        return productDto;
    }

    @Override
    public Product partialUpdate(ProductDto productDto, Product product) {
        if (productDto == null) {
            return product;
        }

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }

        return product;
    }
}
