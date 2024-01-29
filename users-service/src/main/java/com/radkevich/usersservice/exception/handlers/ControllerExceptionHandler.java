package com.radkevich.usersservice.exception.handlers;

import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.exception.SendMessageBrokerException;
import com.radkevich.usersservice.exception.models.ErrorMessage;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

/**
 * Глобальный обработчик исключений в рамках контроллеров Spring.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Обработка всех исключений
     *
     * @param exception- RetryableException
     * @return ответ клиентской стороне, закрывающий детали ошибки
     */

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> globalException(Exception exception) {
//        logException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
//        return new ResponseEntity<>(ErrorMessage.toErrorResponse(exception), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(SendMessageBrokerException.class)
    public ResponseEntity<Object> messageBrokerException(SendMessageBrokerException exception) {
        logException(HttpStatus.SERVICE_UNAVAILABLE, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.SERVICE_UNAVAILABLE);
    }


    public static void logException(HttpStatus httpStatus, Exception exception) {
        log.info(InfoMessagesConstants.COMMON_ERROR_LOG_MESSAGE, httpStatus.value(), exception.getMessage());
        log.debug(InfoMessagesConstants.COMMON_ERROR_LOG_DETAIL_MESSAGE, exception);
    }
}
