package com.radkevich.adminservice.exceptions;


import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class CourierNotFoundException extends TemplateException {
    public CourierNotFoundException(Object...args) {
        super(ErrorMessage.COURIER_NOT_FOUND, args);
    }
}
