package com.app.parking.repository;

import com.app.parking.entity.BookingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, UUID> {

    @Query("SELECT COUNT(bh) FROM BookingHistory bh WHERE bh.user.userId = :userId AND bh.parkingData.parkingId = :parkingId")
    int checkBooking(UUID userId, UUID parkingId);

    @Query("SELECT bh FROM BookingHistory bh JOIN FETCH bh.parkingData WHERE bh.user.userId = :userId")
    Page<BookingHistory> getBookingHistory(UUID userId, Pageable pageable);
}
