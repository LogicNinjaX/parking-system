package com.app.parking.event.listener;

import com.app.parking.event.BookingEvent;
import com.app.parking.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventListener {

    private final EmailService emailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingEventListener.class);

    public BookingEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleBookingEvent(BookingEvent event){
        LOGGER.info("Booking event listener started successfully");
        emailService.sendBookingEmail(event.getParkingData(), event.getUser(), event.getBookingResponse());
    }
}
