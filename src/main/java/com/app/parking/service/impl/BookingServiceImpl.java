package com.app.parking.service.impl;

import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.response.BookingResponse;
import com.app.parking.entity.BookingHistory;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.entity.Wallet;
import com.app.parking.event.publisher.BookingEventPublisher;
import com.app.parking.exception.custom_exception.*;
import com.app.parking.repository.BookingHistoryRepository;
import com.app.parking.repository.ParkingRepository;
import com.app.parking.repository.UserRepository;
import com.app.parking.repository.WalletRepository;
import com.app.parking.service.BookingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BookingHistoryRepository historyRepository;
    private final BookingEventPublisher bookingEventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    public BookingServiceImpl(ParkingRepository parkingRepository, UserRepository userRepository, WalletRepository walletRepository, BookingHistoryRepository historyRepository, BookingEventPublisher bookingEventPublisher) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.historyRepository = historyRepository;
        this.bookingEventPublisher = bookingEventPublisher;
    }

    @Transactional
    @Override
    public BookingResponse bookParking(UUID userId, UUID parkingId, BookingRequest request){

        ParkingData parking = parkingRepository.getParkingWithOwner(parkingId)
                .orElseThrow(() -> new ParkingNotFoundException(parkingId));

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        validateBooking(parkingId, parking);

        Wallet userWallet = userRepository.findWalletByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user: "+userId));

        long bill = parking.getPrice() * request.getDuration();

        validateBalance(userWallet.getBalance(), bill, userId);

        Wallet ownerWallet = parking.getOwner().getWallet();

        var response = generateBookingResponse(request.getDuration(), bill);

        parking.setBooked(true);
        userWallet.setBalance(userWallet.getBalance() - bill);
        ownerWallet.setBalance(ownerWallet.getBalance() + bill);
        try {
            parkingRepository.save(parking);
            walletRepository.saveAll(List.of(userWallet, ownerWallet));
            historyRepository.save(
                    new BookingHistory(user, parking, bill, response.getBookedAt(), response.getBookedAt().plusHours(request.getDuration()))
            );
            walletRepository.flush();
            parkingRepository.flush();
            LOGGER.info("Parking [{}] booked successfully by user [{}]", parkingId, userId);
        }catch (Exception e){
            LOGGER.error("Booking failed: {}", e.getMessage(), e);
            throw new BookingFailedException("Booking failed for paring spot: "+parkingId);
        }

        bookingEventPublisher.publish(parking, user, response);
        return response;
    }

    public void validateBooking(UUID parkingId, ParkingData parkingData){
        if (parkingData.isBooked()){
            LOGGER.warn("Parking spot: [{}] is already booked", parkingId);
            throw new ParkingAlreadyBookedException("Parking already booked");
        }
    }

    public void validateBalance(double balance, long bill, UUID userId){
        if (balance < bill){
            LOGGER.warn("User: [{}] has insufficient wallet balance", userId);
            throw new BalanceErrorException("insufficient wallet balance");
        }
    }

    public BookingResponse generateBookingResponse(int bookingDuration, long bill){
        Duration duration = Duration.ofHours(bookingDuration);
        LocalDateTime bookedAt = LocalDateTime.now();
        LocalDate endsDate = bookedAt.plus(duration).toLocalDate();
        LocalTime endTime = bookedAt.plus(duration).toLocalTime();

        return new BookingResponse.Builder()
                .bill(bill)
                .duration(bookingDuration)
                .bookedAt(bookedAt)
                .endDate(endsDate)
                .endTime(endTime)
                .build();
    }
}
