package com.SE.ITHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<APIError> handleContactAlreadyExistsException(ContactAlreadyExistsException e){
        return new ResponseEntity<>(new APIError("CONTACT_ALREADY_EXISTS", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<APIError> handleContactNotFoundException(ContactNotFoundException e){
        return new ResponseEntity<>(new APIError("CONTACT_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleException(Exception e){
        return new ResponseEntity<>(new APIError("INTERNAL_SERVER_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
