package com.radkevich.couriersservice.exceptions;


import com.radkevich.couriersservice.exceptions.models.ErrorMessage;

public class BadCredentialsException extends TemplateException {
    public BadCredentialsException(Exception ex) {
        super(ErrorMessage.USER_AUTH_ERROR, ex);
    }
}
