package com.besysoft.product_store.configuration;

import com.besysoft.product_store.utils.StringToEnumConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new StringToEnumConverter());
        }
    }
