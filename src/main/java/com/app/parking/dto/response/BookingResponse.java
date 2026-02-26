package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Schema(
        name = "BookingResponse",
        description = "Represents booking confirmation details after a successful parking booking",
        example = """
                {
                  "totalBill": 2500,
                  "duration": 5,
                  "bookedAt": "2026-02-25T10:30:00",
                  "bookingEndDate": "2026-02-25",
                  "bookingEndTime": "15:30:00"
                }
                """
)
public class BookingResponse {

    @Schema(
            description = "Total bill amount calculated for the booking",
            example = "2500",
            minimum = "0"
    )
    private Long totalBill;

    @Schema(
            description = "Booking duration in hours",
            example = "5",
            minimum = "1"
    )
    private Integer duration;

    @Schema(
            description = "Date and time when the booking was created",
            type = "string",
            format = "date-time",
            example = "2026-02-25T10:30:00"
    )
    private LocalDateTime bookedAt;

    @Schema(
            description = "Date when the booking will end",
            type = "string",
            format = "date",
            example = "2026-02-25"
    )
    private LocalDate bookingEndDate;

    @Schema(
            description = "Time when the booking will end",
            type = "string",
            format = "time",
            example = "15:30:00"
    )
    private LocalTime bookingEndTime;

    public BookingResponse(Builder builder){
        this.totalBill = builder.totalBill;
        this.duration = builder.duration;
        this.bookedAt = builder.bookedAt;
        this.bookingEndDate = builder.bookingEndDate;
        this.bookingEndTime = builder.bookingEndTime;
    }

    public Long getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Long totalBill) {
        this.totalBill = totalBill;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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
