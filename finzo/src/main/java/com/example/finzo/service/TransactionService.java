package com.example.finzo.service;

import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;

public interface TransactionService {
    String depositToAccount(TransactionDto transactionDto);

    String withdrawFromAccount(TransactionDto transactionDto);

    TransactionEntity depositAccountToAccount(AccountToAccountDto accountToAccountDto);
}
