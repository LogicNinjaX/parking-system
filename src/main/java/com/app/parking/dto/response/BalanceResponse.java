package com.app.parking.dto.response;

import java.time.LocalDateTime;

public class BalanceResponse {

    private Double balance;

    private LocalDateTime updatedAt;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
