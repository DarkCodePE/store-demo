package com.codmind.api_order.converters;

import com.codmind.api_order.dtos.ProductDTO;
import com.codmind.api_order.entity.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO>{

    @Override
    public ProductDTO frontEntity(Product entity) {
        if (entity == null) return null;
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Product frontDTO(ProductDTO dto) {
        if (dto == null) return null;
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

}
