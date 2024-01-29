package com.radkevich.usersservice.exception.handlers;


import com.radkevich.usersservice.exception.CancelOrderException;
import com.radkevich.usersservice.exception.OrderNotFoundException;
import com.radkevich.usersservice.exception.SendMessageBrokerException;
import com.radkevich.usersservice.exception.models.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrdersExceptionHandler extends ControllerExceptionHandler{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CancelOrderException.class)
    public ResponseEntity<Object> messageBrokerException(CancelOrderException exception) {
        logException(HttpStatus.BAD_REQUEST, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException exception) {
        logException(HttpStatus.NOT_FOUND, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }
}
