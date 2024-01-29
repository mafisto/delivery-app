package com.radkevich.couriersservice.exceptions;


import com.radkevich.couriersservice.exceptions.models.ErrorMessage;

public class OrderNotFoundException extends TemplateException {
    public OrderNotFoundException(Exception ex) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, ex);
    }
    public OrderNotFoundException(Object ... args) {
        super(ErrorMessage.ORDER_NOT_FOUND_ERROR, args);
    }
}
