package com.radkevich.couriersservice.exceptions;


import com.radkevich.couriersservice.exceptions.models.ErrorMessage;

public class ChangeOrderStatusException extends TemplateException {
    public ChangeOrderStatusException(Exception ex) {
        super(ErrorMessage.CHANGE_ORDER_STATUS_ERROR, ex);
    }
    public ChangeOrderStatusException(Object ... args) {
        super(ErrorMessage.CHANGE_ORDER_STATUS_ERROR, args);
    }
}
