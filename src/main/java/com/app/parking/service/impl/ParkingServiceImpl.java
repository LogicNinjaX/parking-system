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
import com.app.parking.service.ParkingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final ParkingMapper parkingMapper;
    private final ListingEventPublisher listingEventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceImpl.class);


    public ParkingServiceImpl(ParkingRepository parkingRepository, UserRepository userRepository, ParkingMapper parkingMapper, ListingEventPublisher listingEventPublisher) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.parkingMapper = parkingMapper;
        this.listingEventPublisher = listingEventPublisher;
    }

    @Transactional
    @Override
    public ListingResponse listParkingSpace(UUID userId, ListingRequest request){
        ParkingData parking = parkingMapper.toParking(request);
        User userReference = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        parking.setOwner(userReference);
        parking = parkingRepository.save(parking);

        try {
            parkingRepository.flush();
        }catch (Exception e){
            throw new RuntimeException("Failed to list parking spot");
        }

        listingEventPublisher.publish(parking);

        return parkingMapper.toListingResponse(parking);
    }

    @Override
    public List<ParkingDataResponse> getAllParkingSpots(Pageable pageable)
    {
        return parkingRepository.findAll(pageable)
                .get()
                .map(parkingMapper::toParkingDataResponse)
                .toList();
    }

    @Override
    public List<ParkingDataResponse> getAvailableParkingSpots(Pageable pageable){
        return parkingRepository.getAvailableParking(pageable)
                .get()
                .map(parkingMapper::toParkingDataResponse)
                .toList();
    }

    @Transactional
    @Override
    public ParkingUpdateResponse updateParking(UUID ownerId, UUID parkingId, ParkingUpdateRequest request){
        ParkingData parking = parkingRepository.getParkingByOwnerId(ownerId, parkingId)
                .orElseThrow(() -> new ParkingNotFoundException("Parking not found created by owner: [%s]".formatted(ownerId.toString())));

        updateValidator(parking, request);

        parking = parkingRepository.saveAndFlush(parking);
        LOGGER.info("Parking: [{}] updated successfully", parking.getParkingId());

        return parkingMapper.toUpdateResponse(parking);
    }

    public void updateValidator(ParkingData parking, ParkingUpdateRequest request){
        if (parking.getDisabled() != request.getDisable()){
            parking.setDisabled(request.getDisable());
        }

        if (parking.getLocationUrl() != null && !parking.getLocationUrl().equals(request.getLocationUrl())){
            parking.setLocationUrl(request.getLocationUrl());
        }

        if (parking.getPrice() != request.getPrice()){
            parking.setPrice(request.getPrice());
        }

        if (!parking.getState().equals(request.getState())){
            parking.setState(request.getState());
        }

        if (!parking.getCity().equals(request.getCity())){
            parking.setCity(request.getCity());
        }

        if (parking.getPincode() != request.getPincode()){
            parking.setPincode(request.getPincode());
        }

        if (parking.getAddress_line() != null && !parking.getAddress_line().equals(request.getAddress_line())){
            parking.setAddress_line(request.getAddress_line());
        }

        parking.setVehicleType(request.getVehicleType());
    }

    @Override
    public void deleteOwnersParking(UUID ownerId, UUID parkingId){
        int updatedRows = parkingRepository.deleteOwnersParking(ownerId, parkingId);

        if (updatedRows > 0){
            LOGGER.info("Parking: [{}] deleted successfully", parkingId);
        }
    }

    @Override
    public void updateParkingStatus(UUID ownerId, UUID parkingId, boolean disable){
        int updatedRows = parkingRepository.updateActivation(ownerId, parkingId, disable);

        if (updatedRows > 0){
            LOGGER.info("Parking: [{}] status updated successfully", parkingId);
        }
    }


    @Override
    public List<ParkingDataResponse> getMyParkingSpots(UUID userId, int page, int size, String sort, String dir){
        Sort sorting = Sort.by(Sort.Direction.ASC, sort);

        if (dir.equalsIgnoreCase("desc")){
            sorting = Sort.by(Sort.Direction.DESC, sort);
        }

        Pageable pageable = PageRequest.of(page, size, sorting);

        return  parkingRepository.getAllUsersParking(userId, pageable)
                .get()
                .map(parkingMapper::toParkingDataResponse)
                .toList();
    }
}
