package com.app.parking.event.listener;

import com.app.parking.event.ListingEvent;
import com.app.parking.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ListingEventListener {

    private final EmailService emailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ListingEventListener.class);

    public ListingEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleListingEvent(ListingEvent event){
        LOGGER.info("Listing event listener started successfully");
        emailService.sendListingEmail(event.getParkingData());
    }
}
