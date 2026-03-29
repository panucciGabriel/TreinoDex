package com.treinodex.backend.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handlerError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity handlerError400(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        var cleanErrors = errors.stream()
                .map(ValidationErrorsData::new)
                .toList();
        return ResponseEntity.badRequest().body(cleanErrors);
    }

    private record ValidationErrorsData(String campo, String mensagem) {
        public ValidationErrorsData(org.springframework.validation.FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
