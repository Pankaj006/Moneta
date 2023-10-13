package com.example.ticketingsystem.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    //Handle invalid request- BadRequest(400)
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorDescription = ex.getMethod();
        ApiException apiException = ApiException.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorDescription(errorDescription)
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notElementFoundException(NoSuchElementException ex) {
        ApiException apiException = ApiException.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorDescription(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }
}
