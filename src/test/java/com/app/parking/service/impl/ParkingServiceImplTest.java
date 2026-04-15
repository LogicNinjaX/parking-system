package com.app.parking.service.impl;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.request.ParkingUpdateRequest;
import com.app.parking.dto.response.ListingResponse;
import com.app.parking.dto.response.ParkingDataResponse;
import com.app.parking.dto.response.ParkingUpdateResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.event.publisher.ListingEventPublisher;
import com.app.parking.exception.custom_exception.ParkingNotFoundException;
import com.app.parking.exception.custom_exception.UserNotFoundException;
import com.app.parking.mapper.ParkingMapper;
import com.app.parking.repository.ParkingRepository;
import com.app.parking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceImplTest {

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ParkingMapper parkingMapper;

    @Mock
    private ListingEventPublisher listingEventPublisher;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    private UUID userId;
    private UUID parkingId;
    private User user;
    private ParkingData parking;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        parkingId = UUID.randomUUID();

        user = new User();
        user.setUserId(userId);

        parking = new ParkingData();
        parking.setParkingId(parkingId);
        parking.setPrice(200L);
        parking.setState("UP");
        parking.setCity("Ghaziabad");
        parking.setPincode(110001);
    }


    @Test
    void listParkingSpace_success() {
        ListingRequest request = new ListingRequest();
        ListingResponse response = new ListingResponse();

        when(parkingMapper.toParking(request)).thenReturn(parking);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(parkingRepository.save(any())).thenReturn(parking);
        when(parkingMapper.toListingResponse(parking)).thenReturn(response);

        ListingResponse result = parkingService.listParkingSpace(userId, request);

        assertNotNull(result);
        verify(listingEventPublisher).publish(parking);
        verify(parkingRepository).flush();
    }


    @Test
    void listParkingSpace_userNotFound() {
        ListingRequest request = new ListingRequest();

        when(parkingMapper.toParking(request)).thenReturn(parking);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> parkingService.listParkingSpace(userId, request));
    }


    @Test
    void listParkingSpace_flushFailure() {
        ListingRequest request = new ListingRequest();

        when(parkingMapper.toParking(request)).thenReturn(parking);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(parkingRepository.save(any())).thenReturn(parking);
        doThrow(new RuntimeException()).when(parkingRepository).flush();

        assertThrows(RuntimeException.class,
                () -> parkingService.listParkingSpace(userId, request));
    }


    @Test
    void getAllParkingSpots_success() {
        Pageable pageable = PageRequest.of(0, 10);

        ParkingDataResponse response = new ParkingDataResponse();

        when(parkingRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(parking)));

        when(parkingMapper.toParkingDataResponse(parking)).thenReturn(response);

        List<ParkingDataResponse> result = parkingService.getAllParkingSpots(pageable);

        assertEquals(1, result.size());
    }


    @Test
    void getAvailableParkingSpots_success() {
        Pageable pageable = PageRequest.of(0, 10);

        ParkingDataResponse response = new ParkingDataResponse();

        when(parkingRepository.getAvailableParking(pageable))
                .thenReturn(new PageImpl<>(List.of(parking)));

        when(parkingMapper.toParkingDataResponse(parking)).thenReturn(response);

        List<ParkingDataResponse> result = parkingService.getAvailableParkingSpots(pageable);

        assertEquals(1, result.size());
    }


    @Test
    void updateParking_success() {
        ParkingUpdateRequest request = new ParkingUpdateRequest();
        request.setCity("Delhi");

        ParkingUpdateResponse response = new ParkingUpdateResponse();

        when(parkingRepository.getParkingByOwnerId(userId, parkingId))
                .thenReturn(Optional.of(parking));
        when(parkingRepository.saveAndFlush(parking)).thenReturn(parking);
        when(parkingMapper.toUpdateResponse(parking)).thenReturn(response);

        ParkingUpdateResponse result = parkingService.updateParking(userId, parkingId, request);

        assertNotNull(result);
        verify(parkingRepository).saveAndFlush(parking);
    }


    @Test
    void updateParking_notFound() {
        ParkingUpdateRequest request = new ParkingUpdateRequest();

        when(parkingRepository.getParkingByOwnerId(userId, parkingId))
                .thenReturn(Optional.empty());

        assertThrows(ParkingNotFoundException.class,
                () -> parkingService.updateParking(userId, parkingId, request));
    }


    @Test
    void deleteOwnersParking_success() {
        when(parkingRepository.getParkingByOwnerId(userId, parkingId))
                .thenReturn(Optional.of(parking));

        parkingService.deleteOwnersParking(userId, parkingId);

        assertTrue(parking.getDisabled());
    }


    @Test
    void deleteOwnersParking_notFound() {
        when(parkingRepository.getParkingByOwnerId(userId, parkingId))
                .thenReturn(Optional.empty());

        assertThrows(ParkingNotFoundException.class,
                () -> parkingService.deleteOwnersParking(userId, parkingId));
    }


    @Test
    void updateParkingStatus_success() {
        when(parkingRepository.updateActivation(userId, parkingId, true))
                .thenReturn(1);

        parkingService.updateParkingStatus(userId, parkingId, true);

        verify(parkingRepository).updateActivation(userId, parkingId, true);
    }

    @Test
    void getMyParkingSpots_success() {
        Pageable pageable = PageRequest.of(0, 10);
        ParkingDataResponse response = new ParkingDataResponse();

        when(parkingRepository.getAllUsersParking(userId, pageable))
                .thenReturn(new PageImpl<>(List.of(parking)));

        when(parkingMapper.toParkingDataResponse(parking)).thenReturn(response);

        List<ParkingDataResponse> result = parkingService.getMyParkingSpots(userId, pageable);

        assertEquals(1, result.size());
    }
}
