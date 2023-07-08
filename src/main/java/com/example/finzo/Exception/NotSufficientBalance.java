package com.example.finzo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.finzo.utils.Constants.NOT_SUFFICIENT;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class NotSufficientBalance extends RuntimeException{
    public NotSufficientBalance(String currentBalance) {
        super(NOT_SUFFICIENT + currentBalance);
    }
}
