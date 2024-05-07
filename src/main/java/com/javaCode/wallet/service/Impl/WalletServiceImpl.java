package com.javaCode.wallet.service.Impl;

import com.javaCode.wallet.dto.OperationType;
import com.javaCode.wallet.dto.WalletRequestDto;
import com.javaCode.wallet.dto.WalletResponseDto;
import com.javaCode.wallet.exceptions.NullIdException;
import com.javaCode.wallet.exceptions.OperationTypeException;
import com.javaCode.wallet.exceptions.WalletNotFoundException;
import com.javaCode.wallet.exceptions.WalletRequestDtoNullException;
import com.javaCode.wallet.repository.WalletRepo;
import com.javaCode.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;

    @Override
    public WalletResponseDto findWallet(UUID id) {
        if (id == null) {
            log.info("Wallet id is null");
            throw new NullIdException("Wallet id is null");
        }
        return walletRepo.findById(id)
                .map(WalletResponseDto::of)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with the id not found"));
    }

    @Transactional
    @Override
    public void updateWallet(WalletRequestDto walletRequestDto) {
        if (walletRequestDto == null) {
            log.info("WalletRequestDto must not be mull");
            throw new WalletRequestDtoNullException("WalletRequestDto must not be mull");
        }
        if (walletRequestDto.getOperationType() == OperationType.DEPOSIT) {
            walletRepo.updatePlusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
            return;
        } else if (walletRequestDto.getOperationType() == OperationType.WITHDRAW) {
            walletRepo.updateMinusAmount(walletRequestDto.getAmount(), walletRequestDto.getWalletId());
            return;
        }
            log.info("Operation not recognized, use: DEPOSIT or WITHDRAW");
            throw new OperationTypeException("Operation not recognized, use: DEPOSIT or WITHDRAW");
    }
}
