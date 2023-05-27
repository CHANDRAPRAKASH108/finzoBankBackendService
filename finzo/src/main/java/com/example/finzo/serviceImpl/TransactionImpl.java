package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.payloads.TransactionDto;
import com.example.finzo.service.TransactionService;
import com.example.finzo.utils.AccountStatus;
import com.example.finzo.utils.TransactionIdGenerator;
import com.example.finzo.utils.TransactionType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.finzo.utils.Constants.*;

@Service
public class TransactionImpl implements TransactionService {
    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Integer depositToAccount(TransactionDto transactionDto) {
        String accountNumber = transactionDto.getReceiverAccountId();
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID :"+accountNumber));
        int beforeDeposit = userAccountEntity.getBalance();
        int afterDeposit = beforeDeposit + transactionDto.getAmount();
        userAccountEntity.setBalance(afterDeposit);
        userAccountRepo.save(userAccountEntity);
        TransactionEntity transactionEntity = this.modelMapper.map(transactionDto, TransactionEntity.class);
        transactionEntity.setTransactionId(TransactionIdGenerator.generateUUID());
        transactionEntity.setTransactionTime(LocalDateTime.now());
        transactionEntity.setTransactionType(TransactionType.DEPOSIT);
        this.transactionRepo.save(transactionEntity);
        return afterDeposit;
    }

    @Override
    public String withdrawFromAccount(TransactionDto transactionDto) {
        String accountNumber = transactionDto.getReceiverAccountId();
        int afterWithdrawal = 0;
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID :"+accountNumber));
        int beforeWithdrawal = userAccountEntity.getBalance();
        if (beforeWithdrawal >= transactionDto.getAmount()){
            if (userAccountEntity.getStatus().equals(AccountStatus.ACTIVE.name())){
                afterWithdrawal = beforeWithdrawal - transactionDto.getAmount();
                userAccountEntity.setBalance(afterWithdrawal);
                userAccountRepo.save(userAccountEntity);
                TransactionEntity transactionEntity = this.modelMapper.map(transactionDto, TransactionEntity.class);
                transactionEntity.setTransactionId(TransactionIdGenerator.generateUUID());
                transactionEntity.setTransactionTime(LocalDateTime.now());
                transactionEntity.setTransactionType(TransactionType.WITHDRAW);
                this.transactionRepo.save(transactionEntity);
                return WITHDRAWN_MESSAGE + afterWithdrawal;
            }else {
                return ACCOUNT_DISABLED;
            }
        }else {
            return NOT_SUFFICIENT + beforeWithdrawal;
        }
    }

    public Integer currentBalanceByAccountNumber(String accountNumber){
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID :"+accountNumber));
        return userAccountEntity.getBalance();
    }
}
