package com.app.parking.exception.custom_exception;

public class FieldUniqueException extends ServiceException{
    public FieldUniqueException(String fieldName) {
        super(fieldName+" must be unique");
    }
}
