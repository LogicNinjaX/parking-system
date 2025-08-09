package com.app.parking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ListingRequest {

    @NotBlank(message = "{parking.list.state.not-blank}")
    private String state;

    @NotBlank(message = "{parking.list.city.not-blank}")
    private String city;

    @Positive(message = "{parking.list.pincode.positive}")
    private int pincode;

    @NotBlank(message = "{parking.list.address_line.not-blank}")
    private String address_line;

    private String locationUrl;

    @Positive(message = "{parking.list.price.positive}")
    private long price;  // per hours

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

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
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
