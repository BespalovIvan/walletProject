package com.javaCode.wallet.service.Impl;

import com.javaCode.wallet.dto.OperationType;
import com.javaCode.wallet.dto.WalletRequestDto;
import com.javaCode.wallet.dto.WalletResponseDto;
import com.javaCode.wallet.exceptions.NullIdException;
import com.javaCode.wallet.exceptions.WalletNotFoundException;
import com.javaCode.wallet.exceptions.WalletRequestDtoException;
import com.javaCode.wallet.repository.WalletRepo;
import com.javaCode.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;

    @Override
    public WalletResponseDto findWallet(UUID id) {
        if (id == null) {
            throw new NullIdException("Wallet id is null");
        }
        return walletRepo.findById(id)
                .map(WalletResponseDto::new)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with the id not found"));
    }

    @Transactional
    @Override
    public void updateWallet(WalletRequestDto walletRequestDto) {
        if (walletRequestDto == null) {
            throw new WalletRequestDtoException("WalletRequestDto must not be mull");
        }
        if (walletRequestDto.getOperationType() == OperationType.DEPOSIT) {
            walletRepo.updatePlusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
        } else if (walletRequestDto.getOperationType() == OperationType.WITHDRAW) {
            walletRepo.updateMinusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
        }
    }

}
