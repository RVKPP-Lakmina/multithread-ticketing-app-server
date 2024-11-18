package com.example.ticketingapp.exception;

import com.example.ticketingapp.util.GlobalLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException e) {
        GlobalLogger.logError(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        GlobalLogger.logError(e.getMessage(), e);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleException(IOException e) {
        GlobalLogger.logError(e.getMessage(), e);
        return new ResponseEntity<>("Input output Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<String> handleException(InterruptedException e) {
        GlobalLogger.logError(e.getMessage(), e);
        return new ResponseEntity<>("Thread Interrupted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
