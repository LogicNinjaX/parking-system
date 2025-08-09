package com.app.parking.repository;

import com.app.parking.entity.ParkingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository extends JpaRepository<ParkingData, UUID> {

    @Query("SELECT p FROM ParkingData p JOIN FETCH p.owner WHERE p.parkingId = :parkingId")
    Optional<ParkingData> getParkingWithOwner(UUID parkingId);

    @Query("SELECT p FROM ParkingData p WHERE p.isBooked = false AND p.isDisabled = false")
    Page<ParkingData> getAvailableParking(Pageable pageable);
}
