package com.codmind.api_order.services;

import com.codmind.api_order.entity.Product;
import com.codmind.api_order.exceptions.DataServiceException;
import com.codmind.api_order.exceptions.GeneralServiceException;
import com.codmind.api_order.exceptions.ValidateServiceException;
import com.codmind.api_order.repository.ProductRepository;
import com.codmind.api_order.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(Pageable pageable){
        return productRepository.findAll(pageable).toList();
    }

    public Product findById(Long productId){
        try{
            //Para ver este log debemos activar logging.level.root= DEBUG en el properties
            log.debug("find by id: ", productId);
            Product product = productRepository.findById(productId).orElseThrow(() -> new DataServiceException("The product does no exist"));
            return product;
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }
    @Transactional
    public Product createOrUpdate(Product request){
        try{
            ProductValidator.createOrUpdate(request);
            if (request.getId() == null){
                Product product = productRepository.save(request);
                return product;
            }
            Product product = productRepository.findById(request.getId()).orElseThrow(() -> new DataServiceException("The product does no exist"));
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            productRepository.save(product);
            return product;
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long productId){
        try{
            Product product = productRepository.findById(productId).orElseThrow(() -> new DataServiceException("The product does no exist"));
            productRepository.delete(product);
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }
}
