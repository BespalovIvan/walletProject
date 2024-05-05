package com.javaCode.wallet.dto;

import com.javaCode.wallet.entity.Wallet;
import lombok.Data;

import java.util.UUID;

@Data
public class WalletResponseDto {
    private final UUID id;
    private final Long amount;

    public WalletResponseDto(Wallet wallet) {
        this.id = wallet.getId();
        this.amount = wallet.getAmount();
    }

}
