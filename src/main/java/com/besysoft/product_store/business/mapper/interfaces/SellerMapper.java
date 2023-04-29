package com.besysoft.product_store.business.mapper.interfaces;

import com.besysoft.product_store.business.dto.SellerDto;
import com.besysoft.product_store.domain.Seller;

public interface SellerMapper {

    Seller toEntity(SellerDto sellerDto);

    SellerDto toDto(Seller seller);

    Seller partialUpdate(SellerDto sellerDto, Seller seller);
}
