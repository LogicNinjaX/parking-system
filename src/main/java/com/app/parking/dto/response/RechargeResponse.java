package com.app.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "RechargeResponse",
        description = "Represents the updated wallet balance after a successful recharge",
        example = """
                {
                  "updatedBalance": 2000.50
                }
                """
)
public class RechargeResponse {

    @Schema(
            description = "Updated wallet balance after recharge",
            example = "2000.50",
            minimum = "0"
    )
    private Double updatedBalance;

    public Double getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(Double updatedBalance) {
        this.updatedBalance = updatedBalance;
    }
}
