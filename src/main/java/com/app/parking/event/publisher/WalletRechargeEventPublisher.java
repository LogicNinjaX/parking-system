package com.app.parking.event.publisher;

import com.app.parking.entity.Wallet;
import com.app.parking.event.WalletRechargeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class WalletRechargeEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletRechargeEventPublisher.class);

    public WalletRechargeEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(Wallet wallet, Long amount, LocalDate localDate, LocalTime localTime){
        LOGGER.info("WalletRechargeEvent published successfully at: {}", LocalDateTime.now());
        eventPublisher.publishEvent(new WalletRechargeEvent(this, wallet, amount, localDate, localTime));
    }
}
