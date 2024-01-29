package com.radkevich.oderservice.exceptions;


import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class OrderNotFoundException extends TemplateException {
    public OrderNotFoundException(Exception ex) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, ex);
    }
    public OrderNotFoundException(Object ... args) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, args);
    }
}
