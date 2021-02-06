package com.example.storedemo.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product")
@Entity
@Data
public class ProductEntity extends BaseEntity{
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private BigDecimal price;
}
