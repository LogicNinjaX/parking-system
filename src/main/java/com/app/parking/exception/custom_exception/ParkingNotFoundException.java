package com.app.parking.exception.custom_exception;

import java.util.UUID;

public class ParkingNotFoundException extends EntityNotFoundException{
    public ParkingNotFoundException(UUID parkingId) {
        super("Parking", parkingId);
    }

    public ParkingNotFoundException(String message) {
        super(message);
    }
}
