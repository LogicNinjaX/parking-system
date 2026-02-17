package com.app.parking.controller;

import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/wallet")
@Tag(name = "Wallet Management", description = "Endpoints related for wallet eg. recharge/fetch balance")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(summary = "Recharge Wallet", description = "Loads balance in user's wallet based on provided amount")
    @PostMapping(path = "/recharge", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<RechargeResponse>> rechargeWallet(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "amount") Long amount
    )
    {
        var response = walletService.rechargeWallet(user.getUserId(), amount);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "recharge successful", response));
    }

    @Operation(summary = "Get Balance", description = "Returns user balance")
    @GetMapping(path = "/balance", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@AuthenticationPrincipal CustomUserDetails user){
        var response = walletService.getBalance(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "balance fetched successfully", response));
    }
}
