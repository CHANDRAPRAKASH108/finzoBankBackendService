package com.example.finzo.utils;

import java.util.UUID;

public class TransactionIdGenerator {
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return uuidString;
    }
}
