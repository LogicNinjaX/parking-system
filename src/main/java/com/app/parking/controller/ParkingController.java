package com.app.parking.controller;

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
@Tag(name = "Parking Management", description = "Endpoints related to booking, listing, deleting parking etc.")
public class ParkingController {

    private final ParkingService parkingService;
    private final BookingService bookingService;

    public ParkingController(ParkingService parkingService, BookingService bookingService) {
        this.parkingService = parkingService;
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get available parking spaces", description = "Returns available/unavailable parking spaces")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
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

    @Operation(summary = "List parking", description = "Registers parking space for listing")
    @PostMapping(path = "/list", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ListingResponse>> listParkingSpot(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ListingRequest request
            )
    {
        var response = parkingService.listParkingSpace(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parking spot saved successfully", response));
    }

    @Operation(summary = "Book space", description = "Books parking space and returns details with bill")
    @PostMapping(path = "/book/{parking-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Update parking", description = "Returns update parking space")
    @PutMapping(path = "/{parking-id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Delete Parking", description = "Deletes parking space based on provided parking id")
    @DeleteMapping(path = "/{parking-id}")
    public ResponseEntity<Void> deleteParking(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("parking-id") UUID parkingId
    ){
        parkingService.deleteOwnersParking(user.getUserId(), parkingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update Status", description = "Updates parking status eg. disabled = true/false")
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

    @Operation(summary = "My Parking Spaces", description = "Returns current users listed parking spots")
    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ParkingDataResponse>>> getMyParkingSpots(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "createdAt", required = false) String sort,
            @RequestParam(defaultValue = "asc", required = false) String dir
    )
    {
        var response = parkingService.getMyParkingSpots(user.getUserId(), page, size,sort, dir);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking data fetched successfully", response));
    }

    @Operation(summary = "Cancel Booking", description = "Returns true after successful cancellation")
    @PostMapping(path = "/cancel", produces = APPLICATION_JSON_VALUE)
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
