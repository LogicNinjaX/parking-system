package com.app.parking.mapper;

import com.app.parking.dto.request.ReviewRequest;
import com.app.parking.dto.response.ReviewDataResponse;
import com.app.parking.entity.ParkingReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "parking", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ParkingReview toReview(ReviewRequest request);

    @Mapping(target = "username", source = "parkingReview.user.username")
    ReviewDataResponse toReviewDataResponse(ParkingReview parkingReview);
}
