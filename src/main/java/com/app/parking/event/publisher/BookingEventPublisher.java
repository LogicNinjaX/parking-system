package com.app.parking.event.publisher;


import com.app.parking.dto.response.BookingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.event.BookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingEventPublisher.class);

    public BookingEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(ParkingData parkingData, User user, BookingResponse bookingResponse){
        LOGGER.info("Booking Event published successfully at: {}", LocalDateTime.now());
        eventPublisher.publishEvent(new BookingEvent(this, parkingData, user, bookingResponse));
    }
}
