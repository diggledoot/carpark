package com.example.carpark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CarParkExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(
            CarParkException carParkException
    ) {
        if (carParkException.getHttpStatus() != null) {
            return new ResponseEntity<>(carParkException.getMessage(), carParkException.getHttpStatus());
        }
        return new ResponseEntity<>(carParkException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
