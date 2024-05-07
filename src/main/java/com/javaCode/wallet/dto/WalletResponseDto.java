package com.javaCode.wallet.dto;

import com.javaCode.wallet.entity.Wallet;
import lombok.Data;

import java.util.UUID;

@Data
public class WalletResponseDto {
    private final UUID id;
    private final Long amount;

    public static WalletResponseDto of(Wallet wallet){
        return new WalletResponseDto(wallet.getId(),wallet.getAmount());
    }

}
