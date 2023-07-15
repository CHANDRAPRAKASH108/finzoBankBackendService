package com.example.finzo.service;

import com.example.finzo.entity.UserAccountResponse;
import com.example.finzo.payloads.UserAccountDto;

import java.util.List;

public interface UserAccountService {
    UserAccountResponse createAccount(UserAccountDto userAccountDto);

    List<UserAccountDto> fetchAllUserAccount();

    UserAccountDto fetchUserAccountById(Integer account);

    UserAccountDto fetchUserAccountByAadhar(String aadharNumber);

    String disableAccount(String accountNumber);
}
