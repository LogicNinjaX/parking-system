package com.app.parking.exception.custom_exception;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException{

    public UserNotFoundException(UUID userId) {
        super("User", userId);
    }
}
