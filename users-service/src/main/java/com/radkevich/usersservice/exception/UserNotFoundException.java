package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class UserNotFoundException extends TemplateException {
    public UserNotFoundException(String username) {
        super(ErrorMessage.USER_NOT_FOUND, username);
    }
}
