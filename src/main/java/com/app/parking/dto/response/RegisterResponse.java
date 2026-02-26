package com.app.parking.dto.response;

import com.app.parking.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "RegisterResponse",
        description = "Represents user details returned after successful registration",
        example = """
                {
                  "fullName": "Nitish Sahni",
                  "username": "nitish123",
                  "email": "example@gmail.com",
                  "role": "USER",
                  "createdAt": "2026-02-25T10:15:30",
                  "updatedAt": "2026-02-25T10:15:30"
                }
                """
)
public class RegisterResponse {

    @Schema(
            description = "Full name of the user",
            example = "Nitish Sahni"
    )
    private String fullName;

    @Schema(
            description = "Unique username of the user",
            example = "nitish123"
    )
    private String username;

    @Schema(
            description = "Registered email address",
            example = "example@gmail.com"
    )
    private String email;

    @Schema(
            description = "Role assigned to the user",
            example = "USER",
            implementation = UserRole.class
    )
    private UserRole role;

    @Schema(
            description = "Date and time when the account was created",
            type = "string",
            format = "date-time",
            example = "2026-02-25T10:15:30"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the account was last updated",
            type = "string",
            format = "date-time",
            example = "2026-02-25T10:15:30"
    )
    private LocalDateTime updatedAt;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
