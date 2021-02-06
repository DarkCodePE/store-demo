package com.example.storedemo.controller;

import com.example.storedemo.controller.dto.PurchaseDTO;
import com.example.storedemo.service.IPurchaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
public class PurchaseController {
    private final IPurchaseService purchaseService;
    @PostMapping("/finishPurchase")
    public ResponseEntity finishPurchase(@Valid @RequestBody PurchaseDTO request){
        log.info("Handling finish purchase request: {}", request);
        Integer orderId = purchaseService.FinishPurchase(request);
        return ResponseEntity.ok().build();
    }
}
