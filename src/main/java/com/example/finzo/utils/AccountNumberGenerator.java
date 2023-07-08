package com.example.finzo.utils;

public class AccountNumberGenerator {
    public static String generateUniqueNumber() {
        // Get current timestamp
        long timestamp = System.currentTimeMillis();

        // Generate a random number between 1000000 and 9999999
        int random = (int) (Math.random() * 8999999) + 1000000;

        // Combine timestamp and random number
        String uniqueNumber = String.valueOf(timestamp) + String.valueOf(random);

        // Return the 7-digit unique number
        return uniqueNumber.substring(0, 7);
    }
}
