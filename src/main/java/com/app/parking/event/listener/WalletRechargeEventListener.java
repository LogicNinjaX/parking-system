package com.app.parking.event.listener;

import com.app.parking.event.WalletRechargeEvent;
import com.app.parking.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WalletRechargeEventListener {

    private final EmailService emailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletRechargeEventListener.class);

    public WalletRechargeEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleRechargeEvent(WalletRechargeEvent event){
        LOGGER.info("Recharge event listener started successfully");
        emailService.sendRechargeEmail(event.getWallet(), event.getAmount(), event.getLocalDate(), event.getLocalTime());
    }
}
