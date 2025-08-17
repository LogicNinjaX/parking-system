package com.app.parking.service;

import com.app.parking.dto.response.BookingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.entity.Wallet;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailService {

    void sendRechargeEmail(Wallet wallet, Long amount, LocalDate localDate, LocalTime localTime);

    void sendListingEmail(ParkingData parkingData);

    void sendBookingEmail(ParkingData parkingData, User user, BookingResponse bookingResponse);
}
