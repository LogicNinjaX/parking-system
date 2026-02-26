package com.app.parking.controller;

import com.app.parking.controller.doc.WallerApiDoc;
import com.app.parking.dto.request.RechargeRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.security.CustomUserDetails;
import com.app.parking.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController implements WallerApiDoc {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(path = "/recharge", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<RechargeResponse>> rechargeWallet(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid RechargeRequest request
    )
    {
        var response = walletService.rechargeWallet(user.getUserId(), request.getAmount());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "recharge successful", response));
    }

    @GetMapping(path = "/balance", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@AuthenticationPrincipal CustomUserDetails user){
        var response = walletService.getBalance(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "balance fetched successfully", response));
    }
}
