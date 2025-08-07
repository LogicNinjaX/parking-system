package com.app.parking.mapper;

import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "updatedBalance", source = "balance")
    RechargeResponse toRechargeResponse(Wallet wallet);

    BalanceResponse toBalanceResponse(Wallet wallet);
}
