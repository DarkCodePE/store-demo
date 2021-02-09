package com.codmind.api_order.controllers;

import com.codmind.api_order.Dtos.ProductDTO;
import com.codmind.api_order.converters.ProductConverter;
import com.codmind.api_order.entity.Product;
import com.codmind.api_order.repository.ProductRepository;
import com.codmind.api_order.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    private ProductConverter productConverter = new ProductConverter();
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOS = productConverter.frontEntity(products);
        return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("productId") Long productId){
        log.info("id:{}", productId);
        Product product = productService.findById(productId);
        ProductDTO productDTO = productConverter.frontEntity(product);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO request){
        Product response = productService.createOrUpdate(productConverter.frontDTO(request));
        ProductDTO productDTO = productConverter.frontEntity(response);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO request){
        Product product = productService.createOrUpdate(productConverter.frontDTO(request));
        ProductDTO productDTO = productConverter.frontEntity(product);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId){
        productService.delete(productId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
