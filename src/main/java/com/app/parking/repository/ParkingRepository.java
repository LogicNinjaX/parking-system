package com.app.parking.repository;

import com.app.parking.entity.ParkingData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<ParkingData, UUID> {
}
