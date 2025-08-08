package com.app.parking.service.impl;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.response.ListingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.mapper.ParkingMapper;
import com.app.parking.repository.ParkingRepository;
import com.app.parking.repository.UserRepository;
import com.app.parking.service.ParkingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final ParkingMapper parkingMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceImpl.class);


    public ParkingServiceImpl(ParkingRepository parkingRepository, UserRepository userRepository, ParkingMapper parkingMapper) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.parkingMapper = parkingMapper;
    }

    @Transactional
    @Override
    public ListingResponse listParkingSpace(UUID userId, ListingRequest request){
        ParkingData parking = parkingMapper.toParking(request);
        User userReference = userRepository.getReferenceById(userId);

        parking.setOwner(userReference);
        parking = parkingRepository.save(parking);

        try {
            parkingRepository.flush();
        }catch (Exception e){
            throw new RuntimeException("Failed to list parking spot");
        }

        return parkingMapper.toListingResponse(parking);
    }
}
