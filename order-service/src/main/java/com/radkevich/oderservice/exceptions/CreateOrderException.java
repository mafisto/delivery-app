package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class CreateOrderException extends TemplateException {
    public CreateOrderException(Throwable ex) {
        super(ErrorMessage.CREATE_ORDER_ERROR, ex);
    }
}
