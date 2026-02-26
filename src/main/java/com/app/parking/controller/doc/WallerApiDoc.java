package com.app.parking.controller.doc;

import com.app.parking.dto.request.RechargeRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.BalanceResponse;
import com.app.parking.dto.response.ErrorResponse;
import com.app.parking.dto.response.RechargeResponse;
import com.app.parking.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Wallet Management", description = "Endpoints for managing user wallet and balance")
@SecurityRequirement(name = "bearerAuth")
public interface WallerApiDoc {

    @Operation(
            summary = "Recharge wallet",
            description = "Adds funds to the authenticated user's wallet"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recharge successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid recharge amount",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponse<RechargeResponse>> rechargeWallet(
            @Parameter(hidden = true)
            CustomUserDetails user,
            RechargeRequest request
    );

    @Operation(
            summary = "Get wallet balance",
            description = "Returns current wallet balance of the authenticated user"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Balance fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponse<BalanceResponse>> getBalance(
            @Parameter(hidden = true)
            CustomUserDetails user
    );
}
