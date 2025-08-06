package com.app.parking.exception.custom_exception;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String entityName, Object identifier) {
        super(entityName+" with id: "+identifier+" not found");
    }
}
