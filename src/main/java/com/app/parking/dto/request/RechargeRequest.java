package com.app.parking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(
        name = "RechargeRequest",
        description = "Request object for wallet recharge",
        example = """
            {
              "amount": 500
            }
            """
)
public class RechargeRequest {

    @Schema(description = "Recharge amount", example = "500", minimum = "1")
    @Positive(message = "Recharge amount must be greater than 0")
    private Long amount;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
