package com.app.parking.event;

import com.app.parking.dto.response.BookingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import org.springframework.context.ApplicationEvent;

public class BookingEvent extends ApplicationEvent {

    private ParkingData parkingData;
    private User user;
    private BookingResponse bookingResponse;

    public BookingEvent(Object source, ParkingData parkingData, User user, BookingResponse bookingResponse) {
        super(source);
        this.parkingData = parkingData;
        this.user = user;
        this.bookingResponse = bookingResponse;
    }

    public ParkingData getParkingData() {
        return parkingData;
    }

    public void setParkingData(ParkingData parkingData) {
        this.parkingData = parkingData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingResponse getBookingResponse() {
        return bookingResponse;
    }

    public void setBookingResponse(BookingResponse bookingResponse) {
        this.bookingResponse = bookingResponse;
    }
}
