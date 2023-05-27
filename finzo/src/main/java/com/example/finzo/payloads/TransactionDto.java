package com.example.finzo.payloads;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Integer amount;
    @Nonnull
    private String receiverAccountId;
}
