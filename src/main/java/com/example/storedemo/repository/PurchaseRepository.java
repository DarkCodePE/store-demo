package com.example.storedemo.repository;

import com.example.storedemo.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
}
