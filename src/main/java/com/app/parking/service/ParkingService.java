package com.app.parking.service;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.request.ParkingUpdateRequest;
import com.app.parking.dto.response.ListingResponse;
import com.app.parking.dto.response.ParkingDataResponse;
import com.app.parking.dto.response.ParkingUpdateResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.exception.custom_exception.ParkingNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParkingService {

    ListingResponse listParkingSpace(UUID userId, ListingRequest request);

    List<ParkingDataResponse> getAllParkingSpots(Pageable pageable);

    List<ParkingDataResponse> getAvailableParkingSpots(Pageable pageable);

    ParkingUpdateResponse updateParking(UUID ownerId, UUID parkingId, ParkingUpdateRequest request) throws ParkingNotFoundException;

    void deleteOwnersParking(UUID ownerId, UUID parkingId);

    void updateParkingStatus(UUID ownerId, UUID parkingId, boolean disable);

    List<ParkingDataResponse> getMyParkingSpots(UUID userId, int page, int size, String sort, String dir);
}
