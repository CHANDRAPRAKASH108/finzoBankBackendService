package com.example.finzo.service;

import com.example.finzo.payloads.TransactionDto;

public interface TransactionService {
    Integer depositToAccount(TransactionDto transactionDto);

    String withdrawFromAccount(TransactionDto transactionDto);

}
