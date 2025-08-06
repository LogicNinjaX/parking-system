package com.app.parking.exception.custom_exception;

public abstract class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}
