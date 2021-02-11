package com.stacklyteam.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "workshop")
@Entity
@Data
public class Workshop extends Base{
    @Column
    private String title;
    @Column
    private String date;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
