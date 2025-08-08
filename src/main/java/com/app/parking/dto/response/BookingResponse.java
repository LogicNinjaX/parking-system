package com.app.parking.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingResponse {

    private long totalBill;

    private int duration;

    private LocalDateTime bookedAt;

    private LocalDate bookingEndDate;

    private LocalTime bookingEndTime;

    public BookingResponse(Builder builder){
        this.totalBill = builder.totalBill;
        this.duration = builder.duration;
        this.bookedAt = builder.bookedAt;
        this.bookingEndDate = builder.bookingEndDate;
        this.bookingEndTime = builder.bookingEndTime;
    }

    public long getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(long totalBill) {
        this.totalBill = totalBill;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public LocalDate getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(LocalDate bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public LocalTime getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(LocalTime bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public static class Builder{
        private long totalBill;

        private int duration;

        private LocalDateTime bookedAt;

        private LocalDate bookingEndDate;

        private LocalTime bookingEndTime;

        public Builder bill(long totalBill){
            this.totalBill = totalBill;
            return this;
        }

        public Builder duration(int duration){
            this.duration = duration;
            return this;
        }

        public Builder bookedAt(LocalDateTime bookedAt){
            this.bookedAt = bookedAt;
            return this;
        }

        public Builder endDate(LocalDate bookingEndDate){
            this.bookingEndDate = bookingEndDate;
            return this;
        }

        public Builder endTime(LocalTime bookingEndTime){
            this.bookingEndTime = bookingEndTime;
            return this;
        }

        public BookingResponse build(){
            return new BookingResponse(this);
        }
    }
}
