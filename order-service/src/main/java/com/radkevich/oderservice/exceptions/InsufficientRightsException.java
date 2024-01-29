package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class InsufficientRightsException extends TemplateException {
    public InsufficientRightsException(String... args) {
        super(ErrorMessage.INSUFFICIENT_RIGHTS_ERROR, args);
    }
}
