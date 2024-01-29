package com.radkevich.adminservice.exceptions.handlers;

import com.radkevich.adminservice.exceptions.CourierNotFoundException;
import com.radkevich.adminservice.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Обработчик исключений для контроллера
 */
@Slf4j
@RestControllerAdvice
public class CouriersExceptionHandler extends ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<Object> badCredentials(CourierNotFoundException exception) {
        logException(HttpStatus.NOT_FOUND, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userExists(UserAlreadyExistsException exception) {
        logException(HttpStatus.NOT_FOUND, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }


}
