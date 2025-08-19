package com.app.parking.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Parking update request")
public class ParkingUpdateRequest {

    @Schema(description = "Parking status value true for enable and false for disable", example = "false")
    private boolean disable;

    @Schema(description = "Parking location url (google maps, apple maps...)", example = "https://maps.google.com/?q=ankurvihar")
    private String locationUrl;

    @Schema(description = "Parking price", example = "500")
    private long price;  // in hours

    @Schema(description = "State", example = "Uttar Pradesh")
    private String state;

    @Schema(description = "Parking city", example = "Lucknow")
    private String city;

    @Schema(description = "Parking pincode", example = "201102")
    private int pincode;

    @Schema(description = "Address line", example = "House no - xyz, colony, landmark")
    private String address_line;

    @Schema(description = "Vehicle types", example = "['Bus', 'Car', 'Bike']")
    private Set<String> vehicleType;

    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public Set<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Set<String> vehicleType) {
        this.vehicleType = vehicleType;
    }
}
