package com.codmind.api_order.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
}
