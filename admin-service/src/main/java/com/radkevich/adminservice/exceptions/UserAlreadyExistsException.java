package com.radkevich.adminservice.exceptions;

import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class UserAlreadyExistsException extends TemplateException {
    public UserAlreadyExistsException(Object... args) {
        super(ErrorMessage.USER_ALREADY_EXISTS, args);
    }
}
