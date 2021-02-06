package com.example.storedemo.service;

import com.example.storedemo.controller.dto.PurchaseDTO;

public interface IPurchaseService {
    Integer FinishPurchase(PurchaseDTO request);
}
