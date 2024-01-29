package com.radkevich.usersservice.exception;


import com.radkevich.usersservice.exception.models.ErrorMessage;

public class SendMessageBrokerException extends TemplateException {
    public SendMessageBrokerException(Throwable ex) {
        super(ErrorMessage.SEND_MESSAGE_ERROR, ex);
    }
}
