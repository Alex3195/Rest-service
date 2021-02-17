package com.alex.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiBadRequsetException extends RuntimeException {
    public ApiBadRequsetException(String message) {
        super(message);
    }
}
