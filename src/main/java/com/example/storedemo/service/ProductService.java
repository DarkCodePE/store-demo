package com.example.storedemo.service;

import com.example.storedemo.entity.ProductEntity;
import com.example.storedemo.exception.NotFoundException;
import com.example.storedemo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @SneakyThrows
    @Override
    public ProductEntity FindById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
