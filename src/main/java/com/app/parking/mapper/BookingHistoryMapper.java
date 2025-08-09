package com.app.parking.mapper;

import com.app.parking.dto.response.BookingHistoryDataResponse;
import com.app.parking.entity.BookingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingHistoryMapper {

    @Mapping(target = "state", source = "bookingHistory.parkingData.state")
    @Mapping(target = "price", source = "bookingHistory.parkingData.price")
    @Mapping(target = "pincode", source = "bookingHistory.parkingData.pincode")
    @Mapping(target = "parkingId", source = "bookingHistory.parkingData.parkingId")
    @Mapping(target = "city", source = "bookingHistory.parkingData.city")
    @Mapping(target = "address_line", source = "bookingHistory.parkingData.address_line")
    BookingHistoryDataResponse toHistoryResponse(BookingHistory bookingHistory);
}
