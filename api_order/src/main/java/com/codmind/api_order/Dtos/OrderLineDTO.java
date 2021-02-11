package com.codmind.api_order.Dtos;

import com.codmind.api_order.entity.Order;
import com.codmind.api_order.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderLineDTO {
    private Long id;
    private ProductDTO product;
    private Double price;
    private Double quantity;
    private Double total;
}
