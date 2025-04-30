package com.example.carpark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CarParkException extends RuntimeException {
    private HttpStatus final httpStatus;

    public CarParkException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarParkException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CarParkException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
