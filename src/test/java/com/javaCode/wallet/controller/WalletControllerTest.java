package com.javaCode.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaCode.wallet.dto.OperationType;
import com.javaCode.wallet.dto.WalletRequestDto;
import com.javaCode.wallet.dto.WalletResponseDto;
import com.javaCode.wallet.entity.Wallet;
import com.javaCode.wallet.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    MockMvc mockmvc;
    @MockBean
    WalletService walletService;
    @Autowired
    ObjectMapper objectMapper;
    private static final UUID id = UUID.fromString("e949c725-8553-4965-a514-1dcb5ac135f9");


    @Test
    void updateAmountPlusToWalletTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 100L, OperationType.DEPOSIT);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isOk())
                .andReturn();
        verify(walletService, times(1)).updateWallet(walletRequestDto);
    }

    @Test
    void updateAmountMinusToWalletTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 100L, OperationType.WITHDRAW);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isOk())
                .andReturn();
        verify(walletService, times(1)).updateWallet(walletRequestDto);
    }

    @Test
    void findByIdTest() throws Exception {
        Wallet wallet = new Wallet(id, 1000L);
        WalletResponseDto walletResponseDto = WalletResponseDto.of(wallet);
        when(this.walletService.findWallet(id)).thenReturn(walletResponseDto);
        mockmvc.perform(get("/api/v1/wallets/{WALLET_UUID}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.amount").value(1000L));
        verify(walletService, times(1)).findWallet(wallet.getId());
    }

    @Test
    void amountIsNullTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, null, OperationType.WITHDRAW);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        MvcResult response = mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = Objects.requireNonNull(response.getResolvedException()).getMessage();
        Assertions.assertTrue(message.contains("default message [amount]"));
        Assertions.assertTrue(message.contains("default message [amount must not be null]"));
    }
    @Test
    void amountIsLessThenOneTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 0L, OperationType.WITHDRAW);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        MvcResult response = mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = Objects.requireNonNull(response.getResolvedException()).getMessage();
        Assertions.assertTrue(message.contains("default message [amount],1]"));
        Assertions.assertTrue(message.contains("default message [amount must be greater than zero]"));
    }
    @Test
    void walletIdIsNullTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(null, 10L, OperationType.WITHDRAW);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        MvcResult response = mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = Objects.requireNonNull(response.getResolvedException()).getMessage();
        Assertions.assertTrue(message.contains("default message [walletId]"));
        Assertions.assertTrue(message.contains("default message [id must not be null]"));

    }
    @Test
    void operationTypeIsNullTest() throws Exception {
        WalletRequestDto walletRequestDto = new WalletRequestDto(id, 10L, null);
        String walletRequestDtoJson = objectMapper.writeValueAsString(walletRequestDto);
        MvcResult response = mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletRequestDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = Objects.requireNonNull(response.getResolvedException()).getMessage();
        Assertions.assertTrue(message.contains("default message [operationType]"));
        Assertions.assertTrue(message.contains("default message [operation type must not be null]"));

    }
}