package com.example.finzo.utils;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.UserAccountEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonMethods {
    @Autowired
    UserAccountRepo userAccountRepo;

    public UserAccountEntity accountExists(String accountNumber){
        return userAccountRepo.findById(accountNumber)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+accountNumber));
    }
}
