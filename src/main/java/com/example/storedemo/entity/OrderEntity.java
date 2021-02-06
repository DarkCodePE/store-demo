package com.example.storedemo.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "order")
@Entity
@Data
public class OrderEntity extends BaseEntity{
    @Column
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
