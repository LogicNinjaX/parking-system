package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request schema")
public class LoginRequest {

    @Schema(description = "Username", example = "pritam")
    @NotBlank(message = "{user.username.not-blank}")
    private String username;

    @Schema(description = "Login password", example = "pritam@123")
    @NotBlank(message = "{user.password.not-blank}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
