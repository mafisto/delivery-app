package com.radkevich.couriersservice.exceptions;


import com.radkevich.couriersservice.exceptions.models.ErrorMessage;
import com.radkevich.couriersservice.exceptions.models.ErrorResponse;

/**
 * Общее исключение для ошибок сервиса.
 */
public abstract class TemplateException extends RuntimeException {

    protected final ErrorMessage errorMessage;
    protected final transient Object[] args;

    protected TemplateException(ErrorMessage message, Object... args) {
        super(message.toLogMessage(args));
        this.errorMessage = message;
        this.args = args;
    }

    protected TemplateException(ErrorMessage message, Throwable throwable, Object... args) {
        super(message.toLogMessage(args), throwable);
        this.errorMessage = message;
        this.args = args;
    }


    public ErrorResponse getErrorResponse() {
        return errorMessage.getErrorResponse(args);
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorMessage != null ? errorMessage.getCode() : null;
    }

    public String getErrorTitle() {
        return errorMessage != null ? errorMessage.renderTitle(args) : null;
    }
}
