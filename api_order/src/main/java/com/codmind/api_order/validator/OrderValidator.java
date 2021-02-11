package com.codmind.api_order.validator;

import com.codmind.api_order.entity.Order;
import com.codmind.api_order.entity.OrderLine;
import com.codmind.api_order.exceptions.ValidateServiceException;

public class OrderValidator {
    public static void createOrUpdate(Order order) {

        if(order.getLines() == null || order.getLines().isEmpty()) {
            throw new ValidateServiceException("Las líneas son requeridas");
        }

        for(OrderLine line: order.getLines()) {
            if(line.getProduct() == null || line.getProduct().getId() == null) {
                throw new ValidateServiceException("El producto es requerido");
            }

            if(line.getQuantity() == null){
                throw new ValidateServiceException("La cantidad es requerido");
            }
            if(line.getQuantity() < 0) {
                throw new ValidateServiceException("La cantidad es incorrecto");
            }
        }
    }
}
