package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "BookingHistoryDataResponse",
        description = "Represents booking history details of a user",
        example = """
                {
                  "bookingId": "550e8400-e29b-41d4-a716-446655440000",
                  "parkingId": "660e8400-e29b-41d4-a716-446655440111",
                  "price": 500,
                  "state": "Uttar Pradesh",
                  "city": "Lucknow",
                  "pincode": 201102,
                  "addressLine": "House no - xyz, colony, landmark",
                  "totalBill": 2500,
                  "bookedAt": "2026-02-25T12:30:00"
                }
                """
)
public class BookingHistoryDataResponse {

    @Schema(
            description = "Unique identifier of the booking",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID bookingId;

    @Schema(
            description = "Unique identifier of the parking",
            example = "660e8400-e29b-41d4-a716-446655440111"
    )
    private UUID parkingId;

    @Schema(
            description = "Parking price per hour",
            example = "500",
            minimum = "0"
    )
    private Long price;  // in hours

    @Schema(
            description = "State where the parking is located",
            example = "Uttar Pradesh"
    )
    private String state;

    @Schema(
            description = "City where the parking is located",
            example = "Lucknow"
    )
    private String city;

    @Schema(
            description = "Postal code of the parking location",
            example = "201102"
    )
    private Integer pincode;

    @Schema(
            description = "Full address of the parking location",
            example = "House no - xyz, colony, landmark"
    )
    private String addressLine;

    @Schema(
            description = "Total bill amount for the booking",
            example = "2500",
            minimum = "0"
    )
    private Long totalBill;

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

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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
