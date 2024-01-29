package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class AssignCourierToOrderException extends TemplateException {
    public AssignCourierToOrderException(Throwable ex) {
        super(ErrorMessage.ASSIGN_COURIER_ERROR, ex);
    }
}
