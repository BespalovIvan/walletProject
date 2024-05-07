package com.javaCode.wallet.service.Impl;

import com.javaCode.wallet.dto.OperationType;
import com.javaCode.wallet.dto.WalletRequestDto;
import com.javaCode.wallet.dto.WalletResponseDto;
import com.javaCode.wallet.entity.Wallet;
import com.javaCode.wallet.exceptions.InvalidIdException;
import com.javaCode.wallet.exceptions.NullIdException;
import com.javaCode.wallet.exceptions.OperationTypeException;
import com.javaCode.wallet.exceptions.WalletRequestDtoNullException;
import com.javaCode.wallet.repository.WalletRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private WalletRepo walletRepo;
    private static final UUID id = UUID.fromString("e949c725-8553-4965-a514-1dcb5ac135f9");

    @Test
    void findWalletByIdTest() {
        Optional<Wallet> optionalWallet = Optional.of(new Wallet(id, 1000L));
        Mockito.when(walletRepo.findById(id)).thenReturn(optionalWallet);
        WalletResponseDto walletResponseDto = walletService.findWallet(id);
        Assertions.assertEquals(id, walletResponseDto.getId());
        Assertions.assertEquals(1000L, walletResponseDto.getAmount());
    }

    @Test
    void findWalletByIdNullTest() {
        Assertions.assertThrows(NullIdException.class, () -> walletService.findWallet(null));
    }

    @Test
    void findWalletByInvalidIdTest() {
        Mockito.when(walletRepo.findById(id)).thenThrow(new InvalidIdException("Wallet with the id not found"));
        Assertions.assertThrows(InvalidIdException.class, () -> walletService.findWallet(id));
    }

    @Test
    void updateWalletMinusAmountTest() {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 100L, OperationType.WITHDRAW);
        walletService.updateWallet(walletRequestDto);
        verify(walletRepo).updateMinusAmount(100L, walletRequestDto.getWalletId());

    }

    @Test
    void updateWalletPlusAmountTest() {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 100L, OperationType.DEPOSIT);
        walletService.updateWallet(walletRequestDto);
        verify(walletRepo).updatePlusAmount(100L, walletRequestDto.getWalletId());

    }
    @Test
    void IncorrectOperationTypeTest() {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 100L,OperationType.UNKNOWN);
        Assertions.assertThrows(OperationTypeException.class,()->walletService.updateWallet(walletRequestDto));
    }

    @Test
    void walletDtoIsNullTest() {
        Assertions.assertThrows(WalletRequestDtoNullException.class, () -> walletService.updateWallet(null));
    }
}