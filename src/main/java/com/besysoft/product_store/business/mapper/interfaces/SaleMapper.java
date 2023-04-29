package com.besysoft.product_store.business.mapper.interfaces;

import com.besysoft.product_store.business.dto.SaleDto;
import com.besysoft.product_store.domain.Sale;

public interface SaleMapper {
    Sale toEntity(SaleDto saleDto);

    SaleDto toDto(Sale sale);

    Sale partialUpdate(SaleDto saleDto, Sale sale);
}
