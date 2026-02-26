package com.app.parking.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "ReviewDataResponse",
        description = "Represents review details for a parking listing",
        example = """
                {
                  "reviewId": "550e8400-e29b-41d4-a716-446655440000",
                  "username": "prince01",
                  "rating": 4,
                  "comment": "Nice parking service with good security.",
                  "createdAt": "2026-02-25T12:45:00"
                }
                """
)
public class ReviewDataResponse {

    @Schema(
            description = "Unique identifier of the review",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID reviewId;

    @Schema(
            description = "Username of the user who submitted the review",
            example = "nitish123"
    )
    private String username;

    @Schema(
            description = "Rating value between 1 and 5",
            example = "4",
            minimum = "1",
            maximum = "5"
    )
    private Integer rating;

    @Schema(
            description = "Rating value between 1 and 5",
            example = "4",
            minimum = "1",
            maximum = "5"
    )
    private String comment;

    @Schema(
            description = "Date and time when the review was created",
            type = "string",
            format = "date-time",
            example = "2026-02-25T12:45:00"
    )
    private LocalDateTime createdAt;

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
