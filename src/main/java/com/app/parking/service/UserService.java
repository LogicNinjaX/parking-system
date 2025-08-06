package com.app.parking.service;

import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.exception.custom_exception.FieldUniqueException;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest request) throws FieldUniqueException;
}
