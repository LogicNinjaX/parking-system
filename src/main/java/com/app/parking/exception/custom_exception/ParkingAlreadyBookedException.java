package com.app.parking.exception.custom_exception;

public class ParkingAlreadyBookedException extends ServiceException{
    public ParkingAlreadyBookedException(String message) {
        super(message);
    }
}
