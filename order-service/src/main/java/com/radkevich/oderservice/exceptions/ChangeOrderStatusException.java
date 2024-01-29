package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class ChangeOrderStatusException extends TemplateException {
    public ChangeOrderStatusException(Throwable ex) {
        super(ErrorMessage.CHANGE_ORDER_STATUS_ERROR, ex);
    }

    public ChangeOrderStatusException() {
        super(ErrorMessage.CHANGE_ORDER_STATUS_ERROR);
    }
}
