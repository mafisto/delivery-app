package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class CreateCourierException extends TemplateException {
    public CreateCourierException(Throwable ex, Object... args) {
        super(ErrorMessage.CREATE_COURIER_ERROR, ex, args);
    }
}
