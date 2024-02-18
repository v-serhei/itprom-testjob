package com.verbitsky.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

import com.verbitsky.exception.ApplicationException;
import com.verbitsky.exception.RepositoryException;
import com.verbitsky.exception.ResourceObjectNotFoundException;

import java.util.Objects;

@Slf4j
@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(value = ApplicationException.class)
    ResponseEntity<String> handleApplicationException(ApplicationException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = RepositoryException.class)
    ResponseEntity<String> handleRepositoryException(RepositoryException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<String> handleResourceNotFoundException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> handleInvalidArgsException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getFieldError();
        String errorMessage = Objects.nonNull(fieldError)
                ? fieldError.getDefaultMessage() :
                exception.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Wrong request data: " + errorMessage);
    }

    @ExceptionHandler(value = {ResourceObjectNotFoundException.class, NoResourceFoundException.class})
    ResponseEntity<String> handleNoResourceFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<String> handleException(Exception exception) {
        logException(exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected service error: " + exception.getMessage());
    }

    private void logException(Exception ex) {
        log.error("Unexpected error: error type: {}, error message: {}",
                ex.getClass(), ex.getMessage());
    }
}
