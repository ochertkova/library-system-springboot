package com.rest_api.fs14backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookUnavailableException extends RuntimeException{
    public BookUnavailableException(String message) {
        super(message);
    }
}
