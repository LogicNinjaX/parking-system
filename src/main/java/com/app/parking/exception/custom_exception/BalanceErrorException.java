package com.app.parking.exception.custom_exception;

public class BalanceErrorException extends ServiceException{
    public BalanceErrorException(String message) {
        super(message);
    }
}
