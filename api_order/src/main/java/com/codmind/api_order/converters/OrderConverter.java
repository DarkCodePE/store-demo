package com.codmind.api_order.converters;

import com.codmind.api_order.Dtos.OrderDTO;
import com.codmind.api_order.Dtos.OrderLineDTO;
import com.codmind.api_order.entity.Order;
import com.codmind.api_order.entity.OrderLine;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter extends AbstractConverter<Order, OrderDTO>{

    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss");
    private ProductConverter productConverter = new ProductConverter();

    @Override
    public OrderDTO frontEntity(Order entity) {
        if (entity == null) return null;
        List<OrderLineDTO> lines = frontOrderLineEntity(entity.getLines());
        return OrderDTO.builder()
                .id(entity.getId())
                .lines(lines)
                .regDate(entity.getRegDate().format(dateTimeFormat))
                .total(entity.getTotal())
                .build();
    }

    @Override
    public Order frontDTO(OrderDTO dto) {
        if (dto == null) return null;
        List<OrderLine> lines = frontOrderLineDTO(dto.getLines());
        return Order.builder()
                .id(dto.getId())
                .lines(lines)
                .total(dto.getTotal())
                .build();
    }

    private List<OrderLineDTO> frontOrderLineEntity(List<OrderLine> lines){
        if (lines == null) return null;
        return lines.stream().map(line -> OrderLineDTO.builder()
                .id(line.getId())
                .price(line.getPrice())
                .product(productConverter.frontEntity(line.getProduct()))
                .quantity(line.getQuantity())
                .total(line.getTotal())
                .build()).collect(Collectors.toList());
    }
    private List<OrderLine> frontOrderLineDTO(List<OrderLineDTO> lines){
       if (lines == null) return null;
       return lines.stream().map(line -> OrderLine.builder()
       .id(line.getId())
       .price(line.getPrice())
       .product(productConverter.frontDTO(line.getProduct()))
       .quantity(line.getQuantity())
       .total(line.getTotal())
       .build()).collect(Collectors.toList());
    }
}
