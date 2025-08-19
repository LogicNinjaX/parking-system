package com.app.parking.controller;

import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BookingHistoryDataResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.BookingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "History Management", description = "Endpoints for fetching booking history")
public class HistoryController {

    private final BookingHistoryService historyService;

    public HistoryController(BookingHistoryService historyService) {
        this.historyService = historyService;
    }

    @Operation(summary = "Booking History", description = "Returns booking history of currently authenticated user")
    @GetMapping(path = "/my", produces = "application/json")
    public ResponseEntity<ApiResponse<List<BookingHistoryDataResponse>>> getMyBookingHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    )
    {
        var historyList = historyService.getBookingHistory(userDetails.getUserId(), page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Booking history fetched successfully", historyList));
    }
}
