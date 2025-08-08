package com.app.parking.repository;

import com.app.parking.entity.ParkingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository extends JpaRepository<ParkingData, UUID> {

    @Query("SELECT p FROM ParkingData p JOIN FETCH p.owner WHERE p.parkingId = :parkingId")
    Optional<ParkingData> getParkingWithOwner(UUID parkingId);
}
