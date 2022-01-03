package com.wagner.tqi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanNotValidException extends Exception{

    public LoanNotValidException(String mensagem) {
        super("Empréstimo não é válido. Motivo: " + mensagem);
    }
}
