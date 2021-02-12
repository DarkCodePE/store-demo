package com.codmind.api_order.validator;

import com.codmind.api_order.entity.User;
import com.codmind.api_order.exceptions.ValidateServiceException;

public class UserValidator {
    public static void signup(User user) {
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidateServiceException("username is required");
        }

        if(user.getUsername().length() > 30 ) {
            throw new ValidateServiceException("The username is too long (max 30)");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidateServiceException("password is required");
        }

        if(user.getPassword().length() > 30 ) {
            throw new ValidateServiceException("The user password is too long (max 30))");
        }
    }
}
