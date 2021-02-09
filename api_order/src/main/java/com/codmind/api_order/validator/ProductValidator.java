package com.codmind.api_order.validator;

import com.codmind.api_order.entity.Product;

public class ProductValidator {
    public static void createOrUpdate(Product product){
        if (product.getName() == null || product.getName().trim().isEmpty()){
            throw new RuntimeException("El nombre es requerido");
        }
        if (product.getName().length() > 100){
            throw new RuntimeException("El nombre es muy extenso");
        }
        if(product.getPrice() == null){
            throw new RuntimeException("El precio es requerido");
        }
        if(product.getPrice() < 0){
            throw new RuntimeException("El precio es incorrecto");
        }
    }
}
