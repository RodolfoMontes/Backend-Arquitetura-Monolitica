package com.montes.montes.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.montes.montes.model.error.ErrorMenssage;
import com.montes.montes.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        
        ErrorMenssage error = new ErrorMenssage("Not Found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
