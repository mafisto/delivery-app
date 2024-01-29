package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class UserAlreadyExistsException extends TemplateException {
    public UserAlreadyExistsException(Object... args) {
        super(ErrorMessage.USER_ALREADY_EXISTS, args);
    }
}
