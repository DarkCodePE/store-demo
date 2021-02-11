package com.codmind.api_order.controllers;

import com.codmind.api_order.Dtos.OrderDTO;
import com.codmind.api_order.converters.OrderConverter;
import com.codmind.api_order.entity.Order;
import com.codmind.api_order.services.OrderService;
import com.codmind.api_order.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderConverter orderConverter = new OrderConverter();
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber,pageSize);
        List<Order> orders = orderService.findAll(page);
        return new WrapperResponse<List<OrderDTO>>(true, "success",orderConverter.frontEntity(orders)).response(HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable(name = "id") Long id){
        Order order = orderService.findById(id);
        return new WrapperResponse<OrderDTO>(true,"success" ,orderConverter.frontEntity(order)).response(HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> create (@RequestBody OrderDTO order){
        Order newOrder = orderService.createOrUpdate(orderConverter.frontDTO(order));
        return new WrapperResponse<OrderDTO>(true, "success", orderConverter.frontEntity(newOrder)).response(HttpStatus.CREATED);
    }

    @PutMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> update (@RequestBody OrderDTO order){
        Order newOrder = orderService.createOrUpdate(orderConverter.frontDTO(order));;
        return new WrapperResponse<OrderDTO>(true, "success", orderConverter.frontEntity(newOrder)).response(HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        orderService.delete(id);
        return new WrapperResponse(true, "success", null).response(HttpStatus.OK);
    }
}
