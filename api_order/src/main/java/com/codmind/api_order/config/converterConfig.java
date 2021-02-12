package com.codmind.api_order.config;

import com.codmind.api_order.converters.OrderConverter;
import com.codmind.api_order.converters.ProductConverter;
import com.codmind.api_order.converters.UserConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class converterConfig {
    @Value("${config.dateTimeFormat}")
    private String dateTimeFormat;

    @Bean
    public ProductConverter getProductConverter(){
        return new ProductConverter();
    }

    @Bean
    public OrderConverter getOrderConverter(){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss");
        return new OrderConverter(formatter, getProductConverter(), getUserConverter());
    }

    @Bean
    public UserConverter getUserConverter(){
        return new UserConverter();
    }
}
