package com.WalletManagement.WalletApi.exceptions;

import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends RuntimeException {
    private String ErrorMessage;

    public NotFoundException(String message) {
        super(message);
        ErrorMessage = message;
    }

    public NotFoundException(){
    }
}