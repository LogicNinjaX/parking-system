package com.app.parking.controller;

import com.app.parking.controller.doc.ParkingApiDoc;
import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.request.ParkingUpdateRequest;
import com.app.parking.dto.response.*;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.BookingService;
import com.app.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parkings")
public class ParkingController implements ParkingApiDoc {

    private final ParkingService parkingService;
    private final BookingService bookingService;

    public ParkingController(ParkingService parkingService, BookingService bookingService) {
        this.parkingService = parkingService;
        this.bookingService = bookingService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Override
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

    @PostMapping(path = "/list", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<ListingResponse>> listParkingSpot(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ListingRequest request
            )
    {
        var response = parkingService.listParkingSpace(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parking spot saved successfully", response));
    }

    @PostMapping(path = "{parking-id}/book", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
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

    @PutMapping(path = "/{parking-id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Override
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
    @Override
    public ResponseEntity<Void> deleteParking(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId
    ){
        parkingService.deleteOwnersParking(user.getUserId(), parkingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{parking-id}")
    @Override
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

    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<List<ParkingDataResponse>>> getMyParkingSpots(
            @AuthenticationPrincipal CustomUserDetails user,
            Pageable pageable
    )
    {
        var response = parkingService.getMyParkingSpots(user.getUserId(), pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking data fetched successfully", response));
    }

    @PostMapping(path = "/cancel", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam UUID parkingId
    )
    {
        var response = bookingService.cancelBooking(user.getUserId(), parkingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
