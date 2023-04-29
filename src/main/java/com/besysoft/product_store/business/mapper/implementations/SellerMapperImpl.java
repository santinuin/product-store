package com.besysoft.product_store.business.mapper.implementations;

import com.besysoft.product_store.business.dto.SellerDto;
import com.besysoft.product_store.business.mapper.interfaces.SellerMapper;
import com.besysoft.product_store.domain.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapperImpl implements SellerMapper {
    @Override
    public Seller toEntity(SellerDto sellerDto) {
        if(sellerDto == null){
            return null;
        }

        Seller seller = new Seller();

        seller.setId(sellerDto.getId());
        seller.setName(sellerDto.getName());
        seller.setSalary(sellerDto.getSalary());

        return seller;
    }

    @Override
    public SellerDto toDto(Seller seller) {
        if(seller == null){
            return null;
        }

        SellerDto sellerDto = new SellerDto();

        sellerDto.setId(seller.getId());
        sellerDto.setName(seller.getName());
        sellerDto.setSalary(seller.getSalary());
        //sellerDto.setCommission();

        return sellerDto;
    }

    @Override
    public Seller partialUpdate(SellerDto sellerDto, Seller seller) {
        if (sellerDto == null){
            return seller;
        }

        if (sellerDto.getName() != null){
            seller.setName(sellerDto.getName());
        }
        if (sellerDto.getSalary() != null){
            seller.setSalary(sellerDto.getSalary());
        }

        return  seller;
    }
}
