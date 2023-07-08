package com.example.finzo.utils;

import java.util.Random;

public class UserIdGenerator {
    public Integer userIdGenerator(){
        int numberOfDigits = 7;
        int minValue = 1000000;
        int maxValue = 9999999;

        Random random = new Random();
        return random.nextInt(maxValue - minValue + 1) + minValue;
    }
}
