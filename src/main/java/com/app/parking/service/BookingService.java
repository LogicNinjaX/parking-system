package com.app.parking.service;

import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BookingResponse;
import com.app.parking.exception.custom_exception.ParkingAlreadyBookedException;
import com.app.parking.exception.custom_exception.ParkingNotFoundException;
import com.app.parking.exception.custom_exception.WalletNotFoundException;

import java.util.UUID;

public interface BookingService {

    BookingResponse bookParking(UUID userId, UUID parkingId, BookingRequest request) throws ParkingNotFoundException, ParkingAlreadyBookedException, WalletNotFoundException;

    ApiResponse<Void> cancelBooking(UUID userId, UUID parkingId);
}
