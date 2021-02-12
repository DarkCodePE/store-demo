package com.example.storedemo.service;

import com.example.storedemo.controller.dto.PurchaseDTO;
import com.example.storedemo.entity.OrderEntity;
import com.example.storedemo.entity.ProductEntity;
import com.example.storedemo.entity.PurchaseEntity;
import com.example.storedemo.repository.OrderRepository;
import com.example.storedemo.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseService implements IPurchaseService{
    private final IProductService productService;
    private final IUserService userService;
    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public Integer FinishPurchase(PurchaseDTO request) {
        log.info("creating order entity from request: {}", request);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserEntity(userService.findOrCreateUser(
                request.getUserName(),
                request.getUserSurname(),
                request.getUserPhone(),
                request.getUserEmail(),
                request.getUserAddress()
        ));
        orderEntity.setComment(request.getUserComment());
        orderEntity = orderRepository.save(orderEntity);

        for (Map.Entry<Integer, Integer> entry : request.getProductCount().entrySet()) {
            Integer k = entry.getKey();
            Integer v = entry.getValue();
            ProductEntity productEntity = productService.FindById(k);
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setProductEntity(productEntity);
            purchaseEntity.setCount(v);
            purchaseEntity.setOrderEntity(orderEntity);
            purchaseEntity.setOrderEntity(orderEntity);
            purchaseRepository.save(purchaseEntity);
        }
        return orderEntity.getId();
    }
}
