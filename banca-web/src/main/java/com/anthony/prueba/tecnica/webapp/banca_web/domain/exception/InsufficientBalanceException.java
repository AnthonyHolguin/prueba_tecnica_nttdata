package com.anthony.prueba.tecnica.webapp.banca_web.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}