package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "BalanceResponse",
        description = "Represents the user's current wallet balance",
        example = """
                {
                  "balance": 1500.75,
                  "updatedAt": "2026-02-25T14:45:00"
                }
                """
)
public class BalanceResponse {

    @Schema(
            description = "Current available wallet balance",
            example = "1500.75",
            minimum = "0"
    )
    private double balance;

    @Schema(
            description = "Date and time when the balance was last updated",
            type = "string",
            format = "date-time",
            example = "2026-02-25T14:45:00"
    )
    private LocalDateTime updatedAt;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
