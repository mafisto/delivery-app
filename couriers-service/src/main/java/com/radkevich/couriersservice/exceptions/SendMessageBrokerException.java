package com.radkevich.couriersservice.exceptions;


import com.radkevich.couriersservice.exceptions.models.ErrorMessage;

public class SendMessageBrokerException extends TemplateException {
    public SendMessageBrokerException(Throwable ex) {
        super(ErrorMessage.SEND_MESSAGE_ERROR, ex);
    }
}
