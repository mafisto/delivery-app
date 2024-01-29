package com.radkevich.adminservice.exceptions;


import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class AssignCourierToOrderException extends TemplateException {

    public AssignCourierToOrderException(Object... args) {
        super(ErrorMessage.ASSIGN_COURIER_TO_ORDER_ERROR, args);
    }
}
