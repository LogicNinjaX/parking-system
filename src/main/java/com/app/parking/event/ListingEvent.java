package com.app.parking.event;

import com.app.parking.entity.ParkingData;
import org.springframework.context.ApplicationEvent;

public class ListingEvent extends ApplicationEvent {

    private ParkingData parkingData;

    public ListingEvent(Object source, ParkingData parkingData) {
        super(source);
        this.parkingData = parkingData;
    }

    public ParkingData getParkingData() {
        return parkingData;
    }

    public void setParkingData(ParkingData parkingData) {
        this.parkingData = parkingData;
    }
}
