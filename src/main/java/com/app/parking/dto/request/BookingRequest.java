package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(
        name = "BookingRequest",
        description = "Request object used to create a parking booking",
        example = """
                {
                  "duration": 5
                }
                """
)
public class BookingRequest {

    @Schema(
            description = "Booking duration in hours",
            example = "5",
            minimum = "1",
            maximum = "999",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Min(value = 1, message = "{booking.duration.min-value}")
    @Max(value = 999, message = "{booking.duration.max-value}")
    private Integer duration; // in hours

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
