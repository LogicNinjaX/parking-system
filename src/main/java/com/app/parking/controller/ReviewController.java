package com.app.parking.controller;

import com.app.parking.controller.doc.ReviewApiDoc;
import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController implements ReviewApiDoc {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "/{parking-id}", consumes = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("parking-id") UUID parkingId,
            @Valid @RequestBody ReviewRequest request
    )
    {
        reviewService.review(userDetails.getUserId(), parkingId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/{parking-id}", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<List<ReviewDataResponse>>> getAllReviewsByParking(
            @PathVariable("parking-id") UUID parkingId,
            Pageable pageable
    )
    {
        var reviewList = reviewService.getAllReviews(parkingId, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "reviews fetched successfully", reviewList));
    }
}
