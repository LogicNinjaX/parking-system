package com.app.parking.service.impl;

import com.app.parking.dto.request.BookingRequest;
import com.app.parking.dto.response.ApiResponse;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private BookingHistoryRepository historyRepository;

    @Mock
    private BookingEventPublisher bookingEventPublisher;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private UUID userId;
    private UUID parkingId;
    private User user;
    private ParkingData parking;
    private Wallet userWallet;
    private Wallet ownerWallet;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        parkingId = UUID.randomUUID();

        user = new User();
        user.setUserId(userId);

        userWallet = new Wallet();
        userWallet.setBalance(1000D);

        ownerWallet = new Wallet();
        ownerWallet.setBalance(500D);

        parking = new ParkingData();
        parking.setParkingId(parkingId);
        parking.setPrice(100L);
        parking.setBooked(false);

        User owner = new User();
        owner.setWallet(ownerWallet);
        parking.setOwner(owner);
    }


    @Test
    void bookParking_success() {
        BookingRequest request = new BookingRequest();
        request.setDuration(2);

        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(userRepository.findWalletByUserId(userId))
                .thenReturn(Optional.of(userWallet));

        BookingResponse response = bookingService.bookParking(userId, parkingId, request);

        assertNotNull(response);
        assertTrue(parking.getBooked());
        verify(walletRepository).saveAll(any());
        verify(historyRepository).save(any());
        verify(bookingEventPublisher).publish(eq(parking), eq(user), any());
    }


    @Test
    void bookParking_parkingNotFound() {
        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.empty());

        assertThrows(ParkingNotFoundException.class,
                () -> bookingService.bookParking(userId, parkingId, new BookingRequest()));
    }


    @Test
    void bookParking_userNotFound() {
        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> bookingService.bookParking(userId, parkingId, new BookingRequest()));
    }


    @Test
    void bookParking_alreadyBooked() {
        parking.setBooked(true);

        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        assertThrows(ParkingAlreadyBookedException.class,
                () -> bookingService.bookParking(userId, parkingId, new BookingRequest()));
    }


    @Test
    void bookParking_walletNotFound() {
        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(userRepository.findWalletByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class,
                () -> bookingService.bookParking(userId, parkingId, new BookingRequest()));
    }


    @Test
    void bookParking_insufficientBalance() {
        BookingRequest request = new BookingRequest();
        request.setDuration(20); // bill = 2000

        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(userRepository.findWalletByUserId(userId))
                .thenReturn(Optional.of(userWallet));

        assertThrows(BalanceErrorException.class,
                () -> bookingService.bookParking(userId, parkingId, request));
    }


    @Test
    void bookParking_failureDuringSave() {
        BookingRequest request = new BookingRequest();
        request.setDuration(2);

        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(userRepository.findWalletByUserId(userId))
                .thenReturn(Optional.of(userWallet));

        doThrow(new RuntimeException())
                .when(parkingRepository).save(any());

        assertThrows(BookingFailedException.class,
                () -> bookingService.bookParking(userId, parkingId, request));
    }

    @Test
    void cancelBooking_success() {
        BookingHistory history = new BookingHistory();
        history.setTotalBill(200L);

        when(historyRepository.getLiveBooking(eq(userId), eq(parkingId), any()))
                .thenReturn(Optional.of(history));
        when(userRepository.findWalletByUserId(userId))
                .thenReturn(Optional.of(userWallet));
        when(parkingRepository.getParkingWithOwner(parkingId))
                .thenReturn(Optional.of(parking));

        ApiResponse<Void> response = bookingService.cancelBooking(userId, parkingId);

        assertTrue(response.getSuccess());
        verify(parkingRepository).saveAndFlush(parking);
        verify(walletRepository).saveAllAndFlush(any());
    }


    @Test
    void cancelBooking_notFound() {
        when(historyRepository.getLiveBooking(eq(userId), eq(parkingId), any()))
                .thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class,
                () -> bookingService.cancelBooking(userId, parkingId));
    }


    @Test
    void cancelBooking_unexpectedError() {
        BookingHistory history = new BookingHistory();

        when(historyRepository.getLiveBooking(eq(userId), eq(parkingId), any()))
                .thenReturn(Optional.of(history));

        when(userRepository.findWalletByUserId(userId))
                .thenThrow(new RuntimeException("DB crash"));

        assertThrows(CancellationFailedException.class,
                () -> bookingService.cancelBooking(userId, parkingId));
    }
}
