package com.example.finzo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction_log")
public class TransactionEntity {
    @Id
    private UUID transactionId;
    private Integer amount;
    private LocalDateTime transactionTime;
    private String receiverAccountId;
    private String senderAccountId;

    @PrePersist
    public void generateId() {
        if (transactionId == null) {
            transactionId = UUID.randomUUID();
        }
    }
}
