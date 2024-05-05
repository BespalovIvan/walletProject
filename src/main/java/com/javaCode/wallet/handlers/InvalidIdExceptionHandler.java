package com.javaCode.wallet.handlers;

import com.javaCode.wallet.exceptions.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class InvalidIdExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleWalletRequestDtoException() {

        return new ResponseEntity<>("Invalid id", HttpStatus.BAD_REQUEST);
    }
}
