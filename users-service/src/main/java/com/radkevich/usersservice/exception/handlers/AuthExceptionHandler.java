package com.radkevich.usersservice.exception.handlers;

import com.radkevich.usersservice.exception.UserAlreadyExistsException;
import com.radkevich.usersservice.exception.BadCredentialsException;
import com.radkevich.usersservice.exception.UserNotFoundException;
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
public class AuthExceptionHandler extends ControllerExceptionHandler{

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentials(BadCredentialsException exception) {
        logException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userExists(UserAlreadyExistsException exception) {
        logException(HttpStatus.NOT_FOUND, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> badCredentials(UserNotFoundException exception) {
        logException(HttpStatus.UNAUTHORIZED, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }

}
