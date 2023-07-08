package com.example.finzo.service;

import com.example.finzo.entity.TransactionEntity;

import java.util.List;

public interface BalanceService {
    Integer fetchBalanceByAccountNumber(String accountNumber);

    Integer fetchBalanceByAadharNumber(String aadharNumber);

    List<TransactionEntity> fetchAllTransactions(String accountNumber);
}
