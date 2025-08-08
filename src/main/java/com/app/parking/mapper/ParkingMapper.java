package com.app.parking.mapper;

import com.app.parking.dto.request.ListingRequest;
import com.app.parking.dto.response.ListingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingMapper {


    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "parkingId", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "booked", ignore = true)
    ParkingData toParking(ListingRequest request);

    ListingResponse toListingResponse(ParkingData parkingData);
}
