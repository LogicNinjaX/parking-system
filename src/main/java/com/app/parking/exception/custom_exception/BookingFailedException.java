package com.app.parking.exception.custom_exception;

public class BookingFailedException extends ServiceException{
    public BookingFailedException(String message) {
        super(message);
    }
}
