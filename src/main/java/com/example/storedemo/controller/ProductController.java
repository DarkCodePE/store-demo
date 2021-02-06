package com.example.storedemo.controller;

import com.example.storedemo.entity.ProductEntity;
import com.example.storedemo.service.IProductService;
import com.example.storedemo.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/products")
    public List<ProductEntity> getProducts(){
        log.info("Handling get all products request");
        return productService.findAll();
    }

    @GetMapping("products/{id}")
    public ProductEntity findById(@PathVariable Integer id){
        log.info("Handling get product by id: {id}", id);
        return productService.FindById(id);
    }
}
