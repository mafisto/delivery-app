package com.radkevich.adminservice.exceptions;


import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class BadCredentialsException extends TemplateException {
    public BadCredentialsException(Exception ex) {
        super(ErrorMessage.USER_AUTH_ERROR, ex);
    }
}
