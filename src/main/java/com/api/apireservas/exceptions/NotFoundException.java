package com.api.apireservas.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    public NotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(){
        super(HttpStatus.NOT_FOUND, "Recurso no encontrado");
    }
}
