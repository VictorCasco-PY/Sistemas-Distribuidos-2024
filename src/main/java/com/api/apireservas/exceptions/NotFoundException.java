package com.api.apireservas.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
