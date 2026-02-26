package com.app.parking.controller.doc;

import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.request.ParkingUpdateRequest;
import com.app.parking.dto.response.*;
import com.app.parking.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Parking Management", description = "Endpoints for managing and booking parking spots")
@SecurityRequirement(name = "bearerAuth")
public interface ParkingApiDoc {

    @Operation(summary = "Get all parking spots",
            description = "Returns paginated list of parking spots. Optionally filter by availability.")
    ResponseEntity<ApiResponse<List<ParkingDataResponse>>> getParkingSpots(
            @ParameterObject Pageable pageable,
            boolean available
    );


    @Operation(summary = "List a new parking spot",
            description = "Creates a new parking listing for the authenticated user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Parking listed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ResponseEntity<ApiResponse<ListingResponse>> listParkingSpot(
            @Parameter(hidden = true)
            CustomUserDetails user,
            ListingRequest request
    );

    @Operation(summary = "Book a parking spot",
            description = "Books a parking spot for the authenticated user")
    ResponseEntity<ApiResponse<BookingResponse>> bookParkingSpot(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @Parameter(description = "ID of the parking spot")
            UUID parkingId,
            BookingRequest request
    );

    @Operation(summary = "Update parking details",
            description = "Updates parking details of the authenticated user's listing")
    ResponseEntity<ApiResponse<ParkingUpdateResponse>> updateParking(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @Parameter(description = "ID of the parking listing")
            UUID parkingId,
            ParkingUpdateRequest request
    );

    @Operation(summary = "Delete parking",
            description = "Deletes a parking listing owned by the authenticated user")
    ResponseEntity<Void> deleteParking(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @Parameter(description = "ID of the parking listing")
            UUID parkingId
    );

    @Operation(summary = "Update parking status",
            description = "Enable or disable a parking listing")
    ResponseEntity<Void> updateParkingStatus(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @Parameter(description = "ID of the parking listing")
            UUID parkingId,
            @Parameter(description = "true = enable, false = disable")
            boolean status
    );

    @Operation(summary = "Get my parking listings",
            description = "Returns paginated parking listings created by the authenticated user")
    ResponseEntity<ApiResponse<List<ParkingDataResponse>>> getMyParkingSpots(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @ParameterObject
            Pageable pageable
    );

    @Operation(summary = "Cancel booking",
            description = "Cancels an active booking for a parking spot")
    ResponseEntity<ApiResponse<Void>> cancelBooking(
            @Parameter(hidden = true)
            CustomUserDetails user,
            @Parameter(description = "ID of the parking spot")
            UUID parkingId
    );
}
