package com.app.parking.exception.custom_exception;

public class CancellationFailedException extends RuntimeException{

    public CancellationFailedException(String message) {
        super(message);
    }
}
