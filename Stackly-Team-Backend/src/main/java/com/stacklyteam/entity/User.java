package com.stacklyteam.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Setter
@Getter
@Builder
@ToString(exclude = "password")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String token;

}
