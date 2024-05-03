package com.javaCode.service.Impl;

import com.javaCode.dto.OperationType;
import com.javaCode.dto.WalletRequestDto;
import com.javaCode.dto.WalletResponseDto;
import com.javaCode.entity.Wallet;
import com.javaCode.repository.WalletRepo;
import com.javaCode.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;

    @Override
    public WalletResponseDto findWallet(UUID id) {
        return walletRepo.findById(id)
                .map(wallet -> new WalletResponseDto(wallet))
                .orElseThrow(RuntimeException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable
    //@Override
    public void updateWallet2(WalletRequestDto walletRequestDto) {
        Wallet wallet = walletRepo.findById(walletRequestDto.getWalletId())
                .orElseThrow(RuntimeException::new);
        if (walletRequestDto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setAmount(walletRequestDto.getAmount() + wallet.getAmount());
        } else if (walletRequestDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getAmount() < walletRequestDto.getAmount()) {
                throw new RuntimeException();
            }
            wallet.setAmount(wallet.getAmount() - walletRequestDto.getAmount());
        }
        walletRepo.save(wallet);
    }

    @Transactional
    @Retryable
    @Override
    public void updateWallet(WalletRequestDto walletRequestDto) {
        if (walletRequestDto.getOperationType() == OperationType.DEPOSIT) {
            walletRepo.updatePlusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
        } else if (walletRequestDto.getOperationType() == OperationType.WITHDRAW) {
            walletRepo.updateMinusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
        }
    }

}
