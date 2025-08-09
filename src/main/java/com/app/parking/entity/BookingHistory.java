package com.app.parking.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_id", referencedColumnName = "parkingId", nullable = false)
    private ParkingData parkingData;

    private long totalBill;

    private LocalDateTime bookedAt;

    public BookingHistory() {
    }

    public BookingHistory(User user, ParkingData parkingData, long totalBill, LocalDateTime bookedAt) {
        this.user = user;
        this.parkingData = parkingData;
        this.totalBill = totalBill;
        this.bookedAt = bookedAt;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParkingData getParkingData() {
        return parkingData;
    }

    public void setParkingData(ParkingData parkingData) {
        this.parkingData = parkingData;
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
