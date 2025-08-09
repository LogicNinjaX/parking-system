package com.app.parking.exception.custom_exception;

import java.util.UUID;

public class ReviewExistException extends ServiceException{
    public ReviewExistException(UUID userId, UUID parkingId) {
        super("Review already exist by user: [%s] for parking: [%s]".formatted(userId.toString(), parkingId.toString()));
    }
}
