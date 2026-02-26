package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "ApiResponse",
        description = "Standard API response wrapper",
        example = """
                {
                  "success": true,
                  "message": "Operation completed successfully",
                  "data": {},
                  "timestamp": "2026-02-25T10:15:30"
                }
                """
)
public class ApiResponse<T> {

    @Schema(
            description = "Indicates whether the request was successful",
            example = "true"
    )
    private Boolean success;

    @Schema(
            description = "Response message describing the result",
            example = "Operation completed successfully"
    )
    private String message;

    @Schema(
            description = "Actual response payload"
    )
    private T data;

    @Schema(
            description = "Time at which the response was generated",
            type = "string",
            format = "date-time",
            example = "2026-02-25T10:15:30"
    )
    private LocalDateTime timestamp;

    public ApiResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
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
