package ru.skypro.lessons.springboot.test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler
    public ResponseEntity<?> employeeNotFoundException(Exception exception) {
exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler (IllegalJsonFileException.class)

    public ResponseEntity<?> badRequest() {
        System.out.println("Err");
        return ResponseEntity.badRequest().build();
    }
//    @ExceptionHandler(value = {IOException.class, RuntimeException.class})
//    public ResponseEntity<?> employeeNotFoundException() {
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



