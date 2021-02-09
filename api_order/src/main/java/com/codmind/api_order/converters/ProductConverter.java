package com.codmind.api_order.converters;

import com.codmind.api_order.Dtos.ProductDTO;
import com.codmind.api_order.entity.Product;

import java.util.List;

public class ProductConverter extends AbstractConverter<Product, ProductDTO>{

    @Override
    public ProductDTO frontEntity(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Product frontDTO(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

}
