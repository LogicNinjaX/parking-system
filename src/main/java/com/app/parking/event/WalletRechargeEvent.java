package com.app.parking.event;

import com.app.parking.entity.Wallet;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;
import java.time.LocalTime;

public class WalletRechargeEvent extends ApplicationEvent {
    private Wallet wallet;
    private Long amount;
    private LocalDate localDate;
    private LocalTime localTime;

    public WalletRechargeEvent(
            Object source,
            Wallet wallet,
            Long amount,
            LocalDate localDate,
            LocalTime localTime
    )
    {
        super(source);
        this.wallet = wallet;
        this.amount = amount;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }
}
