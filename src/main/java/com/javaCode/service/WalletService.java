package com.javaCode.service;


import com.javaCode.dto.WalletRequestDto;
import com.javaCode.dto.WalletResponseDto;

import java.util.UUID;

public interface WalletService {
    WalletResponseDto findWallet(UUID id);

    void updateWallet(WalletRequestDto walletRequestDto);

}
