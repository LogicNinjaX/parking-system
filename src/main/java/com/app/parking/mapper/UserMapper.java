package com.app.parking.mapper;

import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "myReviews", ignore = true)
    @Mapping(target = "bookingHistory", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "parkingList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toUser(RegisterRequest request);

    RegisterResponse toResponse(User user);
}
