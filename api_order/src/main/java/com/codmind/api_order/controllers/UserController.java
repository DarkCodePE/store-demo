package com.codmind.api_order.controllers;

import com.codmind.api_order.dtos.LoginRequestDTO;
import com.codmind.api_order.dtos.LoginResponseDTO;
import com.codmind.api_order.dtos.SignupDTO;
import com.codmind.api_order.dtos.UserDTO;
import com.codmind.api_order.converters.UserConverter;
import com.codmind.api_order.entity.User;
import com.codmind.api_order.services.UserService;
import com.codmind.api_order.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping("/signup")
    public ResponseEntity<WrapperResponse<UserDTO>> signup (@RequestBody SignupDTO signupDTO){
        User user = userService.create(userConverter.signup(signupDTO));
        return new WrapperResponse<UserDTO>(true, "success", userConverter.frontEntity(user)).response(HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<WrapperResponse<LoginResponseDTO>> login (@RequestBody LoginRequestDTO request){
        LoginResponseDTO response = userService.login(request);
        return new WrapperResponse<LoginResponseDTO>(true, "success", response).response(HttpStatus.OK);
    }
}
