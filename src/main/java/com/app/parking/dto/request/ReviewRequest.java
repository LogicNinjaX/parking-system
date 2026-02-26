package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ReviewRequest",
        description = "Request object used to submit a review for a parking listing",
        example = """
                {
                  "rating": 4,
                  "comment": "Nice parking service with good security."
                }
                """
)
public class ReviewRequest {

    @Schema(
            description = "Rating value between 1 and 5",
            example = "4",
            minimum = "1",
            maximum = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Min(value = 1, message = "{review.rating.min}")
    @Max(value = 5, message = "{review.rating.max}")
    @Positive(message = "{review.rating.positive}")
    private Integer rating;

    @Schema(
            description = "Optional review comment",
            example = "Nice parking service with good security."
    )
    @Size(max = 500, message = "Review comment cannot exceed 500 characters.")
    private String comment;

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
