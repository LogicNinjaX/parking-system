package com.app.parking.event.publisher;


import com.app.parking.entity.ParkingData;
import com.app.parking.event.ListingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ListingEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(ListingEventPublisher.class);

    public ListingEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(ParkingData parkingData){
        LOGGER.info("Listing Event published successfully at: {}", LocalDateTime.now());
        eventPublisher.publishEvent(new ListingEvent(this, parkingData));
    }
}
