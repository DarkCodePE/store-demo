package com.example.storedemo.service;

import com.example.storedemo.entity.UserEntity;

public interface IUserService {
    UserEntity findOrCreateUser(String name, String surname, String phone, String email, String address);
}
