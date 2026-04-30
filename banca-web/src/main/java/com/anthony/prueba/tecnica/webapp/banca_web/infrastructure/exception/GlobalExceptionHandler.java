package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.exception;
 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.exception.InsufficientBalanceException;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InsufficientBalanceException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInsufficientBalance(InsufficientBalanceException ex) {
        // Creamos un objeto de respuesta uniforme
        ErrorResponse error = new ErrorResponse(
            "400", 
            ex.getMessage(),
            LocalDateTime.now()
        );
        
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error));
    }

    public record ErrorResponse(String code, String message, LocalDateTime timestamp) {
    }
}