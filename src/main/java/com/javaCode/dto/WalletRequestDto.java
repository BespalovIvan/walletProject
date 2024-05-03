package com.javaCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.UUID;
@Data
public class WalletRequestDto {
    @JsonProperty(value = "wallet_id")
    @NotNull
    private UUID walletId;
    @NotNull
    @Min(1)
    private Long amount;
    @JsonProperty(value = "operation_type")
    @NotNull
    private OperationType operationType;
}
