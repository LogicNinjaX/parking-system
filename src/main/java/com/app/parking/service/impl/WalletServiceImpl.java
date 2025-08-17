package com.app.parking.service.impl;

import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.entity.Wallet;
import com.app.parking.event.publisher.WalletRechargeEventPublisher;
import com.app.parking.exception.custom_exception.WalletNotFoundException;
import com.app.parking.mapper.WalletMapper;
import com.app.parking.repository.UserRepository;
import com.app.parking.repository.WalletRepository;
import com.app.parking.service.WalletService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;
    private final WalletRechargeEventPublisher rechargeEventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository, WalletMapper walletMapper, WalletRechargeEventPublisher rechargeEventPublisher) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.walletMapper = walletMapper;
        this.rechargeEventPublisher = rechargeEventPublisher;
    }


    @Transactional
    @Override
    public RechargeResponse rechargeWallet(UUID userId, Long amount){
        Wallet wallet = userRepository.findWalletByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user id: "+userId));

        double updatedAmount = wallet.getBalance() + amount;
        wallet.setBalance(updatedAmount);
        wallet =  walletRepository.save(wallet);

        walletRepository.flush();
        LOGGER.info("Wallet recharge completed successfully for user: {}", userId);
        rechargeEventPublisher.publish(wallet, amount, LocalDate.now(), LocalTime.now());
        return walletMapper.toRechargeResponse(wallet);
    }

    @Override
    public BalanceResponse getBalance(UUID userId){
        Wallet wallet =userRepository.findWalletByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user id: "+userId));

        return walletMapper.toBalanceResponse(wallet);
    }
}
