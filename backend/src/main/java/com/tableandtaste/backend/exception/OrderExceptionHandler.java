package com.tableandtaste.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> orderNotFoundException(
            OrderNotFoundException o) {

        return new ResponseEntity<>(
                o.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
