package com.app.parking.dto.response;

import com.app.parking.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Schema(
        name = "ParkingUpdateResponse",
        description = "Represents the updated state of a parking listing",
        example = """
                {
                  "parkingId": "660e8400-e29b-41d4-a716-446655440111",
                  "disabled": false,
                  "booked": true,
                  "locationUrl": "https://maps.google.com/?q=xyz",
                  "price": 600,
                  "state": "Uttar Pradesh",
                  "city": "Lucknow",
                  "pincode": 201102,
                  "addressLine": "New address, landmark",
                  "vehicleType": ["CAR", "BIKE"],
                  "createdAt": "2026-02-25T10:30:00",
                  "updatedAt": "2026-02-25T11:45:00"
                }
                """
)
public class ParkingUpdateResponse {

    @Schema(
            description = "Unique identifier of the parking listing",
            example = "660e8400-e29b-41d4-a716-446655440111"
    )
    private UUID parkingId;

    @Schema(
            description = "Indicates whether the parking listing is disabled",
            example = "false"
    )
    private Boolean disabled;

    @Schema(
            description = "Indicates whether the parking is currently booked",
            example = "true"
    )
    private Boolean booked;

    @Schema(
            description = "Google Maps / Apple Maps location URL",
            example = "https://maps.google.com/?q=xyz"
    )
    private String locationUrl;

    @Schema(
            description = "Updated parking price per hour",
            example = "600",
            minimum = "0"
    )
    private Long price;

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
    private int pincode;

    @Schema(
            description = "Full address of the parking location",
            example = "New address, landmark"
    )
    private String addressLine;

    @ArraySchema(
            schema = @Schema(
                    description = "Supported vehicle type",
                    example = "CAR",
                    implementation = VehicleType.class
            )
    )
    private Set<VehicleType> vehicleType;

    @Schema(
            description = "Date and time when the listing was created",
            type = "string",
            format = "date-time",
            example = "2026-02-25T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the listing was last updated",
            type = "string",
            format = "date-time",
            example = "2026-02-25T11:45:00"
    )
    private LocalDateTime updatedAt;

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public boolean getBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
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

    public Set<VehicleType> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Set<VehicleType> vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
