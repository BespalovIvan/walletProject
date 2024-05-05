package com.javaCode.wallet.handlers;

import com.javaCode.wallet.exceptions.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletNotFoundExceptionHandler {
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<String> handleWalletRequestDtoException() {

        return new ResponseEntity<>("Wallet with the id not found", HttpStatus.BAD_REQUEST);
    }
}
