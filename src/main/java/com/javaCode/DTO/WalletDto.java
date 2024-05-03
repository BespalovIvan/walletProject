package com.javaCode.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletDto {
    private UUID id;
    private Long amount;
    private String operationType;
}
