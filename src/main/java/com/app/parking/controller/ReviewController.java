package com.app.parking.controller;

import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "/{parking-id}", consumes = "application/json")
    public ResponseEntity<Void> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("parking-id") UUID parkingId,
            @Valid @RequestBody ReviewRequest request
    )
    {
        reviewService.review(userDetails.getUserId(), parkingId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/{parking-id}", produces = "application/json")
    public ResponseEntity<ApiResponse<List<ReviewDataResponse>>> getAllReviewsByParking(
            @PathVariable("parking-id") UUID parkingId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    )
    {
        var reviewList = reviewService.getAllReviews(parkingId, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "reviews fetched successfully", reviewList));
    }
}
