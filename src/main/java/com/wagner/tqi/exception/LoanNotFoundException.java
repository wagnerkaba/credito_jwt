package com.wagner.tqi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundException extends Exception{

    public LoanNotFoundException(Long id) {
        super("Empréstimo não encontrado com id:" + id);
    }
}
