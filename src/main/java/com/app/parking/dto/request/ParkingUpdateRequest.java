package com.app.parking.dto.request;


import com.app.parking.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.Set;

@Schema(
        name = "ParkingUpdateRequest",
        description = "Request object used to update an existing parking listing. Only provided fields will be updated.",
        example = """
                {
                  "disabled": true,
                  "locationUrl": "https://maps.google.com/?q=ankurvihar",
                  "price": 600,
                  "state": "Uttar Pradesh",
                  "city": "Lucknow",
                  "pincode": 201102,
                  "addressLine": "New address, landmark",
                  "vehicleType": ["CAR", "BIKE"]
                }
                """
)
public class ParkingUpdateRequest {

    @Schema(
            description = "Set to true to disabled parking, false to enable",
            example = "true"
    )
    private boolean disabled;

    @Schema(
            description = "Google Maps / Apple Maps location URL",
            example = "https://maps.google.com/?q=xyz"
    )
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Location URL must be a valid URL"
    )
    private String locationUrl;

    @Schema(
            description = "Updated parking price per hour",
            example = "600",
            minimum = "1"
    )
    @Positive(message = "{parking.list.price.positive}")
    private long price;  // in hours

    @Schema(
            description = "Updated state of the parking location",
            example = "Uttar Pradesh"
    )
    private String state;

    @Schema(
            description = "Updated city of the parking location",
            example = "Lucknow"
    )
    private String city;

    @Schema(
            description = "Updated postal code",
            example = "201102",
            minimum = "1"
    )
    @Positive(message = "{parking.list.pincode.positive}")
    private int pincode;

    @Schema(
            description = "Updated address line",
            example = "New address, landmark"
    )
    private String addressLine;

    @Schema(description = "Updated supported vehicle type")
    private Set<VehicleType> vehicleType;

    public boolean getDisable() {
        return disabled;
    }

    public void setDisable(boolean disabled) {
        this.disabled = disabled;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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
}
