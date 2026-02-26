package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Set;

@Schema(
        name = "ListingRequest",
        description = "Request object used to create a parking listing",
        example = """
                {
                  "state": "Uttar Pradesh",
                  "city": "Lucknow",
                  "pincode": 201102,
                  "addressLine": "House no - xyz, colony, landmark",
                  "locationUrl": "https://maps.google.com/?q=xyz",
                  "price": 500,
                  "vehicleType": ["CAR", "BIKE"]
                }
                """
)
public class ListingRequest {

    @Schema(
            description = "State where parking is located",
            example = "Uttar Pradesh",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{parking.list.state.not-blank}")
    private String state;

    @Schema(
            description = "City where parking is located",
            example = "Lucknow",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{parking.list.city.not-blank}")
    private String city;

    @Schema(
            description = "Postal code of the parking location",
            example = "201102",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Positive(message = "{parking.list.pincode.positive}")
    private int pincode;

    @Schema(
            description = "Full address of the parking location",
            example = "House no - xyz, colony, landmark",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{parking.list.address_line.not-blank}")
    private String addressLine;

    @Schema(
            description = "Google Maps / Apple Maps location URL",
            example = "https://maps.google.com/?q=ankurvihar"
    )
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Location URL must be a valid URL"
    )
    private String locationUrl;

    @Schema(
            description = "Parking price per hour",
            example = "500",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Positive(message = "{parking.list.price.positive}")
    private long price;  // per hours

    @ArraySchema(
            schema = @Schema(
                    description = "Supported vehicle type",
                    example = "CAR"
            ),
            minItems = 1,
            maxItems = 10
    )
    @NotEmpty(message = "{parking.list.vehicle-type.not-empty}")
    @Size(min = 1, max = 10, message = "{parking.list.vehicle-type.size}")
    private Set<@NotBlank(message = "{parking.list.vehicle-type}") String> vehicleType;

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

    public Set<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Set<String> vehicleType) {
        this.vehicleType = vehicleType;
    }
}
