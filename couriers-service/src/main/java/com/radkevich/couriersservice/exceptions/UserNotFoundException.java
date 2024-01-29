package com.radkevich.couriersservice.exceptions;

import com.radkevich.couriersservice.exceptions.models.ErrorMessage;

public class UserNotFoundException extends TemplateException {
    public UserNotFoundException(String username) {
        super(ErrorMessage.USER_NOT_FOUND, username);
    }
}
