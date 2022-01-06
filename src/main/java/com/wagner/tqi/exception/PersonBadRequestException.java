package com.wagner.tqi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonBadRequestException extends Exception{

    public PersonBadRequestException(String mensagem) {
        super(mensagem);
    }
}
