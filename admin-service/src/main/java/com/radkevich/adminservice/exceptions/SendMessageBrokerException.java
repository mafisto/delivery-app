package com.radkevich.adminservice.exceptions;


import com.radkevich.adminservice.exceptions.models.ErrorMessage;

public class SendMessageBrokerException extends TemplateException {
    public SendMessageBrokerException(Throwable ex) {
        super(ErrorMessage.SEND_MESSAGE_ERROR, ex);
    }
}
