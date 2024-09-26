package com.api.rest.conveniencestore.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //cria uma classe especifica para tratamento de excessões
public class ErrorHandling {

    @ExceptionHandler(EntityNotFoundException.class) //GET indica qual o tipo de exception deve capturar
    public ResponseEntity error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //POST PUT
    public ResponseEntity error400(MethodArgumentNotValidException exception) {
        var error = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(ErrorResponse::new).toList());
    }

    private record ErrorResponse(String field, String message) {

        public ErrorResponse(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}