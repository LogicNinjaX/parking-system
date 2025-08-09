package com.app.parking.service;

import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.exception.custom_exception.BookingNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    void review(UUID userId, UUID parkingId, ReviewRequest request) throws BookingNotFoundException;

    List<ReviewDataResponse> getAllReviews(UUID parkingId, int page, int size);
}
