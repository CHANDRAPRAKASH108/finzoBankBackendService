package com.example.finzo.service;

import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;

public interface TransactionService {
    TransactionEntity depositToAccount(TransactionDto transactionDto);

    TransactionEntity withdrawFromAccount(TransactionDto transactionDto);

    TransactionEntity depositAccountToAccount(AccountToAccountDto accountToAccountDto);
}
