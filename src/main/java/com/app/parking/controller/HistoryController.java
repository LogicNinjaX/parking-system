package com.app.parking.controller;

import com.app.parking.controller.doc.HistoryApiDoc;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BookingHistoryDataResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.BookingHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class HistoryController implements HistoryApiDoc {

    private final BookingHistoryService historyService;

    public HistoryController(BookingHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<List<BookingHistoryDataResponse>>> getMyBookingHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(sort = {"bookedAt"}) Pageable pageable
    )
    {
        var historyList = historyService.getBookingHistory(userDetails.getUserId(), pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Booking history fetched successfully", historyList));
    }
}
