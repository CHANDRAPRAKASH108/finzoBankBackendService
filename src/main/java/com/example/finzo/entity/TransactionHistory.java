package com.example.finzo.entity;

import com.example.finzo.utils.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TransactionHistory {
    private UUID transactionId;
    private Integer amount;
    private TransactionType transactionType;
    private Integer currentBalance;
    private LocalDateTime transaction_time;

}
