package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.TransactionHistoryRepo;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.TransactionHistory;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.service.BalanceService;
import com.example.finzo.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BalanceImpl implements BalanceService {
    @Autowired
    UserAccountRepo userAccountRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    TransactionHistoryRepo transactionHistoryRepo;

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
    public List<TransactionHistory> fetchAllTransactions(String accountNumber) {
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        List<Object[]> result = transactionHistoryRepo.getTransactionHistory(userAccountEntity.getId());
        List<TransactionHistory> transactionResults = new ArrayList<>();
        for (Object[] row : result) {
            TransactionHistory transactionResult = new TransactionHistory();
            transactionResult.setTransactionId((UUID) row[0]);
            transactionResult.setAmount((Integer) row[1]);
            transactionResult.setTransactionType((TransactionType) row[2]);
            transactionResult.setCurrentBalance((Integer) row[3]);
            transactionResult.setTransaction_time((LocalDateTime) row[4]);
            transactionResults.add(transactionResult);
        }
        Collections.sort(transactionResults, Comparator.comparing(TransactionHistory::getTransaction_time));
        return transactionResults;
    }
}
