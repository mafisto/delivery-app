package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class CancelOrderException extends TemplateException {
    public CancelOrderException(Exception ex) {
        super(ErrorMessage.CANCEL_ORDER_ERROR, ex);
    }

    public CancelOrderException() {
        super(ErrorMessage.CANCEL_ORDER_ERROR);
    }
}
