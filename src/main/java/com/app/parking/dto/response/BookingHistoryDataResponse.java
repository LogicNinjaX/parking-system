package com.app.parking.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingHistoryDataResponse {

    private UUID bookingId;

    private UUID parkingId;

    private Long price;  // in hours

    private String state;

    private String city;

    private int pincode;

    private String address_line;

    private long totalBill;

    private LocalDateTime bookedAt;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public long getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(long totalBill) {
        this.totalBill = totalBill;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
