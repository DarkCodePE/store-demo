package com.codmind.api_order.controllers;

import com.codmind.api_order.dtos.ProductDTO;
import com.codmind.api_order.converters.ProductConverter;
import com.codmind.api_order.entity.Product;
import com.codmind.api_order.services.ProductService;
import com.codmind.api_order.utils.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConverter productConverter;

    @GetMapping("/products")
    //Paginate
    // /products?pageNumber=0&pageNumber=10
    public ResponseEntity<WrapperResponse<List<ProductDTO>>> findAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productService.findAll(pageable);
        List<ProductDTO> productDTOS = productConverter.frontEntity(products);
        return new WrapperResponse<List<ProductDTO>>(true, "Success", productDTOS)
                .response(HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable("productId") Long productId){
        log.info("id:{}", productId);
        Product product = productService.findById(productId);
        ProductDTO productDTO = productConverter.frontEntity(product);
        WrapperResponse<ProductDTO> response = new WrapperResponse<>();
        return new WrapperResponse<ProductDTO>(true, "Success", productDTO)
                .response(HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<WrapperResponse<ProductDTO>> create(@RequestBody ProductDTO request){
        Product response = productService.createOrUpdate(productConverter.frontDTO(request));
        ProductDTO productDTO = productConverter.frontEntity(response);
        return new WrapperResponse<ProductDTO>(true, "Success", productDTO)
                .response(HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<WrapperResponse<ProductDTO>> update(@RequestBody ProductDTO request){
        Product product = productService.createOrUpdate(productConverter.frontDTO(request));
        ProductDTO productDTO = productConverter.frontEntity(product);
        return new WrapperResponse<ProductDTO>(true, "Success", productDTO)
                .response(HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<WrapperResponse<Void>> delete(@PathVariable Long productId){
        productService.delete(productId);
        return new WrapperResponse<Void>(true, "Success", null)
                .response(HttpStatus.OK);
    }
}
