package com.app.parking.service;

import com.app.parking.dto.response.BookingHistoryDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookingHistoryService {

    List<BookingHistoryDataResponse> getBookingHistory(UUID userId, Pageable pageable);
}
