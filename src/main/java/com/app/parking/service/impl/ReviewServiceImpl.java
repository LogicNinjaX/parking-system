package com.app.parking.service.impl;

import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.entity.ParkingReview;
import com.app.parking.exception.custom_exception.BookingNotFoundException;
import com.app.parking.exception.custom_exception.ReviewExistException;
import com.app.parking.mapper.ReviewMapper;
import com.app.parking.repository.BookingHistoryRepository;
import com.app.parking.repository.ParkingRepository;
import com.app.parking.repository.ReviewRepository;
import com.app.parking.repository.UserRepository;
import com.app.parking.service.ReviewService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingHistoryRepository historyRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    public ReviewServiceImpl(ReviewRepository reviewRepository, BookingHistoryRepository historyRepository, ParkingRepository parkingRepository, UserRepository userRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.historyRepository = historyRepository;
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    @Override
    public void review(UUID userId, UUID parkingId, ReviewRequest request){
        int historyCount = historyRepository.checkBooking(userId, parkingId);

        validateBooking(historyCount, userId, parkingId);

        int reviewCount = reviewRepository.countReview(userId, parkingId);
        validateReview(reviewCount, userId, parkingId);

        var parking = parkingRepository.getReferenceById(parkingId);
        var user = userRepository.getReferenceById(userId);

        ParkingReview parkingReview = reviewMapper.toReview(request);
        parkingReview.setUser(user);
        parkingReview.setParking(parking);

        reviewRepository.saveAndFlush(parkingReview);
        LOGGER.info("Parking review saved successfully by user: [{}] for parking: [{}]", userId, parkingId);
    }

    public void validateBooking(int historyCount, UUID userId, UUID parkingId){
        if (historyCount == 0){
            LOGGER.error("No booking records found by user: [{}] in parking: [{}]", userId, parkingId);
            throw new BookingNotFoundException(userId, parkingId);
        }
    }

    public void validateReview(int reviewCount, UUID userId, UUID parkingId){
        if (reviewCount > 0){
            LOGGER.error("Review exist by user: [{}] for parking: [{}]", userId, parkingId);
            throw new ReviewExistException(userId, parkingId);
        }
    }

    @Override
    public List<ReviewDataResponse> getAllReviews(UUID parkingId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findReviewsByParkingId(parkingId, pageable)
                .get()
                .map(reviewMapper::toReviewDataResponse)
                .toList();
    }
}
