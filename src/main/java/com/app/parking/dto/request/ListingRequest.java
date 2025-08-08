package com.app.parking.dto.request;

import java.util.Set;

public class ListingRequest {

    private String state;

    private String city;

    private Integer pincode;

    private String address_line;

    private String locationUrl;

    private Long price;  // per hours

    private Set<String> vehicleType;

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

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Set<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Set<String> vehicleType) {
        this.vehicleType = vehicleType;
    }
}
