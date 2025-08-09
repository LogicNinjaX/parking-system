package com.app.parking.service;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.response.ListingResponse;
import com.app.parking.dto.response.ParkingDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParkingService {

    ListingResponse listParkingSpace(UUID userId, ListingRequest request);

    List<ParkingDataResponse> getAllParkingSpots(Pageable pageable);

    List<ParkingDataResponse> getAvailableParkingSpots(Pageable pageable);
}
