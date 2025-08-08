package com.app.parking.service;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.response.ListingResponse;

import java.util.UUID;

public interface ParkingService {

    ListingResponse listParkingSpace(UUID userId, ListingRequest request);
}
