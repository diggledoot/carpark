/* (C)2025 */
package com.example.carpark.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CarParkException extends RuntimeException {
	private final HttpStatus httpStatus;

	public CarParkException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
        this.httpStatus = httpStatus;
    }

	public CarParkException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
