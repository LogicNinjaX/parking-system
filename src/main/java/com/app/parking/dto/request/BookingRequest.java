package com.app.parking.dto.request;

public class BookingRequest {
    private int duration; // in hours

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
