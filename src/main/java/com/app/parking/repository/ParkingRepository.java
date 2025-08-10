package com.app.parking.repository;

import com.app.parking.entity.ParkingData;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository extends JpaRepository<ParkingData, UUID> {

    @Query("SELECT p FROM ParkingData p JOIN FETCH p.owner WHERE p.parkingId = :parkingId")
    Optional<ParkingData> getParkingWithOwner(UUID parkingId);

    @Query("SELECT p FROM ParkingData p WHERE p.isBooked = false AND p.isDisabled = false")
    Page<ParkingData> getAvailableParking(Pageable pageable);

    @Query("SELECT p FROM ParkingData p WHERE p.owner.userId = :ownerId AND p.parkingId = :parkingId")
    Optional<ParkingData> getParkingByOwnerId(UUID ownerId, UUID parkingId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ParkingData p WHERE p.owner.userId = :ownerId AND p.parkingId = :parkingId")
    int deleteOwnersParking(UUID ownerId, UUID parkingId);

    @Transactional
    @Modifying
    @Query("UPDATE ParkingData p SET p.isDisabled = :disable WHERE p.parkingId = :parkingId AND p.owner.userId = :ownerId")
    int updateActivation(UUID ownerId, UUID parkingId, boolean disable);
}
