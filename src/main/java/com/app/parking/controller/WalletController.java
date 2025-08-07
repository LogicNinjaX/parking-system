package com.app.parking.controller;

import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.WalletService;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/recharge")
    public ResponseEntity<ApiResponse<RechargeResponse>> rechargeWallet(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "amount") Long amount
    )
    {
        var response = walletService.rechargeWallet(user.getUserId(), amount);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "recharge successful", response));
    }

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@AuthenticationPrincipal CustomUserDetails user){
        var response = walletService.getBalance(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "balance fetched successfully", response));
    }
}
