package com.app.parking.repository;

import com.app.parking.entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, UUID> {

    @Query("SELECT COUNT(bh) FROM BookingHistory bh WHERE bh.user.userId = :userId AND bh.parkingData.parkingId = :parkingId")
    int checkBooking(UUID userId, UUID parkingId);
}
