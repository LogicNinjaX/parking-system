package com.app.parking.repository;

import com.app.parking.entity.ParkingReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ParkingReview, UUID> {

    @Query("SELECT COUNT(pr) FROM ParkingReview pr WHERE pr.user.userId = :userId AND pr.parking.parkingId = :parkingId")
    int countReview(UUID userId, UUID parkingId);
}
