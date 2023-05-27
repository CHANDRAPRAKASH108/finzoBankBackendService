package com.example.finzo.service;

import com.example.finzo.payloads.UserAccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserAccountService {
    UserAccountDto createAccount(UserAccountDto userAccountDto);
    List<UserAccountDto> fetchAllUserAccount();
    UserAccountDto fetchUserAccountById(Integer account);
    UserAccountDto fetchUserAccountByAadhar(String aadharNumber);
    String disableAccount(String accountNumber);
}
