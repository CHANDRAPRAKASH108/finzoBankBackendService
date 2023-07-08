package com.example.finzo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccountStatusException extends RuntimeException{
    public AccountStatusException() {
        super("Account is not active, please visit our nearest branch to re-activate your bank account");
    }
}
