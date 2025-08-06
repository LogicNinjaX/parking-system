package com.app.parking.repository;

import com.app.parking.entity.ParkingReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ParkingReview, UUID> {
}
