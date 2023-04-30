package com.besysoft.product_store.utils;

import com.besysoft.product_store.domain.CategoryEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, CategoryEnum> {

    @Override
    public CategoryEnum convert(String source) {
        return CategoryEnum.valueOf(source.toUpperCase());
    }
}
