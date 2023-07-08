package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceImpl implements BalanceService {
    @Autowired
    UserAccountRepo userAccountRepo;
    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public Integer fetchBalanceByAccountNumber(String accountNumber) {
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        return userAccountEntity.getBalance();
    }

    @Override
    public Integer fetchBalanceByAadharNumber(String aadharNumber) {
        Integer currentBalance = 0;
        try {
            UserAccountEntity userAccountEntity = userAccountRepo.findByAadharNumber(aadharNumber);
            currentBalance = userAccountEntity.getBalance();
        } catch (Exception ignored) {
            throw new ResourceNotFoundException("User not found with provided aadhar number " + aadharNumber);
        }

        return currentBalance;
    }

    @Override
    public List<TransactionEntity> fetchAllTransactions(String accountNumber) {
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        return transactionRepo.findByReceiverAccountId(accountNumber);
    }
}
