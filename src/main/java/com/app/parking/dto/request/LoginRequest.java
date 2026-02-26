package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "LoginRequest",
        description = "Request object used to authenticate a user and generate a JWT token",
        example = """
                {
                  "username": "nitish",
                  "password": "string"
                }
                """
)
public class LoginRequest {

    @Schema(
            description = "Registered username of the user",
            example = "nitish",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{user.username.not-blank}")
    private String username;

    @Schema(
            description = "User account password",
            example = "string",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
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
