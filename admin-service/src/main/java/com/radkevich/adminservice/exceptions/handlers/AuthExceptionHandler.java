package com.radkevich.adminservice.exceptions.handlers;

import com.radkevich.adminservice.exceptions.BadCredentialsException;
import com.radkevich.adminservice.exceptions.UserNotFoundException;
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
public class AuthExceptionHandler extends ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentials(BadCredentialsException exception) {
        logException(HttpStatus.UNAUTHORIZED, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> badCredentials(UserNotFoundException exception) {
        logException(HttpStatus.UNAUTHORIZED, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }


}
