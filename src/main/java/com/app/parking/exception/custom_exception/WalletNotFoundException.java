package com.app.parking.exception.custom_exception;

public class WalletNotFoundException extends EntityNotFoundException{

    public WalletNotFoundException(String message) {
        super(message);
    }
}
