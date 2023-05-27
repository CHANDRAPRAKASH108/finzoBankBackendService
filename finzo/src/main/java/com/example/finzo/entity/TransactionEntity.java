package com.example.finzo.entity;

import com.example.finzo.utils.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    @Id
    private String transactionId;
    private Integer amount;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
    private String receiverAccountId;
}
