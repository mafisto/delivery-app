package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class OrderNotFoundException extends TemplateException {
    public OrderNotFoundException(Exception ex) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, ex);
    }
    public OrderNotFoundException(Object ... args) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, args);
    }
}
