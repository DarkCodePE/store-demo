package com.example.storedemo.service;

import com.example.storedemo.entity.ProductEntity;

import java.util.List;

public interface IProductService {
    List<ProductEntity> findAll();
    ProductEntity FindById(Integer id);
}
