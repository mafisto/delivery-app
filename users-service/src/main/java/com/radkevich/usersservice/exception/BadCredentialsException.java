package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class BadCredentialsException extends TemplateException {
    public BadCredentialsException(Exception ex) {
        super(ErrorMessage.USER_AUTH_ERROR, ex);
    }
}
