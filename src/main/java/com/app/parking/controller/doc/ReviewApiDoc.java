package com.app.parking.controller.doc;

import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.ErrorResponse;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Review Management", description = "Endpoints for managing parking reviews")
public interface ReviewApiDoc {

    @Operation(
            summary = "Create a review",
            description = "Allows an authenticated user to submit a review for a parking spot"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Review created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Parking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> createReview(
            @Parameter(hidden = true) CustomUserDetails userDetails,
            @Parameter(description = "ID of the parking spot")
            UUID parkingId,
            ReviewRequest request
    );

    @Operation(
            summary = "Get reviews by parking ID",
            description = "Returns paginated list of reviews for a specific parking spot"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reviews fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Parking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponse<List<ReviewDataResponse>>> getAllReviewsByParking(
            @Parameter(description = "ID of the parking spot")
            UUID parkingId,
            @ParameterObject Pageable pageable
    );
}
