package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Schema(description = "Booking request schema")
public class BookingRequest {

    @Schema(description = "Booking duration", example = "5")
    @Min(value = 1, message = "{booking.duration.min-value}")
    @Max(value = 999, message = "{booking.duration.max-value}")
    @Positive(message = "{booking.duration.positive}")
    private int duration; // in hours

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
