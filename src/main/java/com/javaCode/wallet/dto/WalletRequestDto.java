package com.javaCode.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.UUID;
@Data
@AllArgsConstructor
public class WalletRequestDto {
    @JsonProperty(value = "wallet_id")
    @NotNull(message = "id must not be null")
    private UUID walletId;
    @NotNull(message = "amount must not be null")
    @Min(value = 1,message = "amount must be greater than zero")
    private Long amount;
    @JsonProperty(value = "operation_type")
    @NotNull(message = "operation type must not be null")
    private OperationType operationType;
}
