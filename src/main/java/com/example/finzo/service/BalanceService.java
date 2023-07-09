package com.example.finzo.service;

import com.example.finzo.entity.TransactionHistory;

import java.util.List;

public interface BalanceService {
    Integer fetchBalanceByAccountNumber(String accountNumber);

    Integer fetchBalanceByAadharNumber(String aadharNumber);

    List<TransactionHistory> fetchAllTransactions(String accountNumber);
}
