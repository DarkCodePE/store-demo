package com.codmind.api_order.services;

import com.codmind.api_order.entity.Product;
import com.codmind.api_order.repository.ProductRepository;
import com.codmind.api_order.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("The product does no exist"));
        return product;
    }
    @Transactional
    public Product createOrUpdate(Product request){
        ProductValidator.createOrUpdate(request);
        if (request.getId() == null){
            Product product = productRepository.save(request);
            return product;
        }
        Product product = productRepository.findById(request.getId()).orElse(null);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void delete(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("The product does no exist"));
        productRepository.delete(product);
    }
}
