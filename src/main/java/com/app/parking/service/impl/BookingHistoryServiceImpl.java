package com.app.parking.service.impl;

import com.app.parking.dto.response.BookingHistoryDataResponse;
import com.app.parking.mapper.BookingHistoryMapper;
import com.app.parking.repository.BookingHistoryRepository;
import com.app.parking.service.BookingHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {

    private final BookingHistoryRepository historyRepository;
    private final BookingHistoryMapper historyMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingHistoryServiceImpl.class);

    public BookingHistoryServiceImpl(BookingHistoryRepository historyRepository, BookingHistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    public List<BookingHistoryDataResponse> getBookingHistory(UUID userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return historyRepository.getBookingHistory(userId, pageable)
                .get()
                .map(historyMapper::toHistoryResponse)
                .toList();
    }
}
