package com.app.parking.dto.response;

import com.app.parking.enums.UserRole;

import java.time.LocalDateTime;

public class RegisterResponse {

    private String username;

    private String password;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
