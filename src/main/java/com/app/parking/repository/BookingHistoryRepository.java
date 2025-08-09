package com.app.parking.repository;

import com.app.parking.entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, UUID> {
}
