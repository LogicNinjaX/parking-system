package com.app.parking.service;

import com.app.parking.dto.response.BookingHistoryDataResponse;

import java.util.List;
import java.util.UUID;

public interface BookingHistoryService {

    List<BookingHistoryDataResponse> getBookingHistory(UUID userId, int page, int size);
}
