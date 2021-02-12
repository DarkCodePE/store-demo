package com.codmind.api_order.converters;

import com.codmind.api_order.dtos.SignupDTO;
import com.codmind.api_order.dtos.UserDTO;
import com.codmind.api_order.entity.User;

public class UserConverter extends AbstractConverter<User, UserDTO>{
    @Override
    public UserDTO frontEntity(User entity) {
        if (entity == null) return null;
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public User frontDTO(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .build();
    }

    public User signup(SignupDTO signupDTO){
        if (signupDTO == null) return null;
        return User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .build();
    }
}
