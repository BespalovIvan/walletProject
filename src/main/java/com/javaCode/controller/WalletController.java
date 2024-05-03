package com.javaCode.controller;

import com.javaCode.dto.WalletRequestDto;
import com.javaCode.dto.WalletResponseDto;
import com.javaCode.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class WalletController {

    private final WalletService walletService;

    @PostMapping("api/v1/wallet")
    public ResponseEntity<Void> updateAmountToWallet(@Validated @RequestBody WalletRequestDto walletRequestDto) {
        walletService.updateWallet(walletRequestDto);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("api/v1/wallets/{WALLET_UUID}")
    public ResponseEntity<WalletResponseDto> findWallet(@PathVariable("WALLET_UUID") UUID id) {// TODO not null
        return new ResponseEntity<>(walletService.findWallet(id), HttpStatus.OK);
    }

}
