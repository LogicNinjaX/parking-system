package com.app.parking.service;

import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.exception.custom_exception.WalletNotFoundException;

import java.util.UUID;

public interface WalletService {

    RechargeResponse rechargeWallet(UUID userId, Long amount) throws WalletNotFoundException;

    BalanceResponse getBalance(UUID userId) throws WalletNotFoundException;
}
