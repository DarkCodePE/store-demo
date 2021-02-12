package com.example.storedemo.service;

import com.example.storedemo.entity.UserEntity;
import com.example.storedemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.storedemo.entity.UserRole.CUSTOMER;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public UserEntity findOrCreateUser(String name, String surname, String phone, String email, String address) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (nonNull(userEntity)){
            return userEntity;
        }
        userEntity = new UserEntity();
        userEntity.setRole(CUSTOMER.name());
        userEntity.setName(name);
        userEntity.setSurname(surname);
        userEntity.setPhone(phone);
        userEntity.setEmail(email);
        userEntity.setAddress(address);
        return userRepository.save(userEntity);
    }
}
