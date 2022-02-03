package com.WalletManagement.WalletApi.exceptions;

import org.springframework.stereotype.Component;

@Component
public class AlreadyExistsException extends RuntimeException {
    private String ErrorMessage;

    public AlreadyExistsException(String message) {
        super(message);
        ErrorMessage = message;
    }

    public AlreadyExistsException(){
    }
}