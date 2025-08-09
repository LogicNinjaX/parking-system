package com.app.parking.repository;

import com.app.parking.entity.ParkingData;
import com.app.parking.entity.ParkingReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ParkingReview, UUID> {

    @Query("SELECT COUNT(pr) FROM ParkingReview pr WHERE pr.user.userId = :userId AND pr.parking.parkingId = :parkingId")
    int countReview(UUID userId, UUID parkingId);

    @Query("SELECT pr FROM ParkingReview pr JOIN FETCH pr.user WHERE pr.parking.parkingId = :parkingId")
    Page<ParkingReview> findReviewsByParkingId(UUID parkingId, Pageable pageable);
}
