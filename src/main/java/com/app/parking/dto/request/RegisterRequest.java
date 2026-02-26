package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(
        name = "RegisterRequest",
        description = "Request object used to register a new user account",
        example = """
                {
                  "fullName": "Nitish Sahni",
                  "username": "nitish",
                  "password": "string",
                  "email": "example@gmail.com"
                }
                """
)
public class RegisterRequest {

    @Schema(
            description = "Full name of the user",
            example = "Nitish Sahni",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{user.full-name.not-blank}")
    private String fullName;

    @Schema(
            description = "Unique username (5-20 characters)",
            example = "nitish",
            minLength = 5,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{user.username.not-blank}")
    @Length(min = 5, max = 20, message = "Username must be between {min} and {max} characters.")
    private String username;

    @Schema(
            description = "Account password (8-20 characters)",
            example = "string",
            minLength = 8,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{user.password.not-blank}")
    @Length(min = 8, max = 20, message = "Password must be between {min} and {max} characters.")
    private String password;

    @Schema(
            description = "Valid email address of the user",
            example = "example@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{user.email.not-blank}")
    @Email(message = "{user.email.invalid}")
    private String email;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
