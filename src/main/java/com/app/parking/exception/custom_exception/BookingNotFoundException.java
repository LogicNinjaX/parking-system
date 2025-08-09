package com.app.parking.exception.custom_exception;

import java.util.UUID;

public class BookingNotFoundException extends EntityNotFoundException{
    public BookingNotFoundException(UUID userId, UUID parkingId) {

        super("Booking not found by user: [%s] in parking: [%s]".formatted(userId.toString(), parkingId.toString()));
    }
}
