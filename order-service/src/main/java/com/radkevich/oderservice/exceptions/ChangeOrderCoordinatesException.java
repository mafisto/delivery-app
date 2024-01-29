package com.radkevich.oderservice.exceptions;

import com.radkevich.oderservice.exceptions.models.ErrorMessage;

public class ChangeOrderCoordinatesException extends TemplateException {
    public ChangeOrderCoordinatesException(Throwable ex) {
        super(ErrorMessage.CHANGE_COORDINATES_ERROR, ex);
    }
}
