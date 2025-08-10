package com.app.parking.controller;

import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.request.ParkingUpdateRequest;
import com.app.parking.dto.response.*;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.BookingService;
import com.app.parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final BookingService bookingService;

    public ParkingController(ParkingService parkingService, BookingService bookingService) {
        this.parkingService = parkingService;
        this.bookingService = bookingService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse<List<ParkingDataResponse>>> getParkingSpots(
            @PageableDefault(sort = {"price"}, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) boolean available
    )
    {
        var parkingList = (available) ?
                parkingService.getAvailableParkingSpots(pageable) : parkingService.getAllParkingSpots(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking spots fetched successfully", parkingList));
    }

    @PostMapping(path = "/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<ListingResponse>> listParkingSpot(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ListingRequest request
            )
    {
        var response = parkingService.listParkingSpace(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parking spot saved successfully", response));
    }

    @PostMapping(path = "/book/{parking-id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<BookingResponse>> bookParkingSpot(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId,
            @Valid @RequestBody BookingRequest request
    )
    {
        var response = bookingService.bookParking(user.getUserId(), parkingId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking spot booked successfully", response));
    }

    @PutMapping(path = "/{parking-id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ApiResponse<ParkingUpdateResponse>> updateParking(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId,
            @Valid @RequestBody ParkingUpdateRequest request
    )
    {
        var response = parkingService.updateParking(user.getUserId(), parkingId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking updated successfully", response));
    }

    @DeleteMapping(path = "/{parking-id}")
    public ResponseEntity<Void> deleteParking(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId
    ){
        parkingService.deleteOwnersParking(user.getUserId(), parkingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(path = "/{parking-id}")
    public ResponseEntity<Void> updateParkingStatus(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId,
            @RequestParam boolean status
    )
    {
        parkingService.updateParkingStatus(user.getUserId(), parkingId, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
