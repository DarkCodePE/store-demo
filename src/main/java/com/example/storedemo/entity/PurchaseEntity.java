package com.example.storedemo.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "purchase")
@Entity
@Data
public class PurchaseEntity extends BaseEntity{
    @Column
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private ProductEntity productEntity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}
