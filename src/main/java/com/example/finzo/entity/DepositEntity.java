package com.example.finzo.entity;

import com.example.finzo.utils.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "deposit_log")
public class DepositEntity {
    @Id
    private UUID id;
    private String accountNumber;
    private Integer amount;
    private Integer currentBalance;
    private UUID transaction_log_id;
    private LocalDateTime transaction_time;
    @Enumerated(EnumType.STRING)
    @Column(name = "transactionType")
    private TransactionType transactionType;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (transaction_time == null) {
            transaction_time = LocalDateTime.now();
        }
        transactionType = TransactionType.valueOf(TransactionType.DEPOSIT.name().toString());
    }
}
