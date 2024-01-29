package com.radkevich.adminservice.exceptions.handlers;


import com.radkevich.adminservice.exceptions.AssignCourierToOrderException;
import com.radkevich.adminservice.exceptions.ChangeOrderStatusException;
import com.radkevich.adminservice.exceptions.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrdersExceptionHandler extends ControllerExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException exception) {
        logException(HttpStatus.NOT_FOUND, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChangeOrderStatusException.class)
    public ResponseEntity<Object> orderNotFoundException(ChangeOrderStatusException exception) {
        logException(HttpStatus.BAD_REQUEST, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AssignCourierToOrderException.class)
    public ResponseEntity<Object> orderNotFoundException(AssignCourierToOrderException exception) {
        logException(HttpStatus.BAD_REQUEST, exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}
