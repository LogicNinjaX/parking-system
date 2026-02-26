package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Standard error response wrapper returned when a request fails",
        example = """
                {
                  "success": false,
                  "message": "Validation failed",
                  "errorCode": 400,
                  "data": null,
                  "timestamp": "2026-02-25T15:20:00"
                }
                """
)
public class ErrorResponse<T> {

    @Schema(
            description = "Indicates whether the request was successful",
            example = "false"
    )
    private Boolean success;

    @Schema(
            description = "Error message describing what went wrong",
            example = "Validation failed"
    )
    private String message;

    @Schema(
            description = "HTTP status code associated with the error",
            example = "400"
    )
    private Integer errorCode;

    @Schema(
            description = "Additional error details (if any)"
    )
    private T data;

    @Schema(
            description = "Time at which the error occurred",
            type = "string",
            format = "date-time",
            example = "2026-02-25T15:20:00"
    )
    private LocalDateTime timestamp;

    public ErrorResponse(Boolean success, String message, Integer errorCode, T data) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
