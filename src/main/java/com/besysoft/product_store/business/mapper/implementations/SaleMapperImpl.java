package com.besysoft.product_store.business.mapper.implementations;

import com.besysoft.product_store.business.dto.SaleDto;
import com.besysoft.product_store.business.mapper.interfaces.SaleMapper;
import com.besysoft.product_store.domain.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapperImpl implements SaleMapper {
    @Override
    public Sale toEntity(SaleDto saleDto) {
        if(saleDto == null){
            return null;
        }

        Sale sale = new Sale();

        sale.setId(saleDto.getId());
        sale.setProduct(saleDto.getProduct());
        sale.setSeller(saleDto.getSeller());
        sale.setQuantity(saleDto.getQuantity());

        return sale;
    }

    @Override
    public SaleDto toDto(Sale sale) {
        if(sale == null){
            return null;
        }

        SaleDto saleDto = new SaleDto();

        saleDto.setId(sale.getId());
        saleDto.setProduct(sale.getProduct());
        saleDto.setSeller(sale.getSeller());
        saleDto.setQuantity(sale.getQuantity());
        saleDto.setCommission(sale.getCommission());
        saleDto.setCreateAt(sale.getCreateAt());

        return saleDto;
    }

    @Override
    public Sale partialUpdate(SaleDto saleDto, Sale sale) {
        if (saleDto == null) {
            return sale;
        }

        if (saleDto.getProduct() != null) {
            sale.setProduct(saleDto.getProduct());
        }
        if (saleDto.getSeller() != null) {
            sale.setSeller(saleDto.getSeller());
        }
        if (saleDto.getQuantity() != null) {
            sale.setQuantity(saleDto.getQuantity());
        }

        return sale;
    }
}
