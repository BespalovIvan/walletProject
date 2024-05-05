package com.javaCode.wallet.service;


import com.javaCode.wallet.dto.WalletRequestDto;
import com.javaCode.wallet.dto.WalletResponseDto;

import java.util.UUID;

public interface WalletService {
    WalletResponseDto findWallet(UUID id);

    void updateWallet(WalletRequestDto walletRequestDto);

}
