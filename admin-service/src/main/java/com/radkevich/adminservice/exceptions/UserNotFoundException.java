package com.radkevich.adminservice.exceptions;

import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class UserNotFoundException extends TemplateException {
    public UserNotFoundException(String username) {
        super(ErrorMessage.USER_NOT_FOUND, username);
    }
}
