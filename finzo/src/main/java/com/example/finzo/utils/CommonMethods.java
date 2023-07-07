package com.example.finzo.utils;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.DepositRepo;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.Repository.WithdrawRepo;
import com.example.finzo.entity.DepositEntity;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.entity.withdrawEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;
import com.example.finzo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonMethods {
    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DepositRepo depositRepo;
    @Autowired
    WithdrawRepo withdrawRepo;
    Logger logger = LoggerFactory.getLogger(TransactionService.class);


    /**
     * This method is about account validation like if there is any account exist with provided account number
     *
     * @param accountNumber
     * @return UserAccountEntity
     */
    public UserAccountEntity fetchUserAccount(String accountNumber) {
        return userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + accountNumber));
    }

    /**
     * This method is about validation of sender and receiver's account if it is not same
     *
     * @param accountToAccountDto
     * @return Boolean
     */
    public Boolean validateAccounts(AccountToAccountDto accountToAccountDto) {
        return !accountToAccountDto.getReceiverAccountId().equals(accountToAccountDto.getSenderAccountId());
    }

    /**
     * This method talks about adding one entry in transaction log table
     *
     * @param accountToAccountDto
     * @return TransactionEntity
     */
    public TransactionEntity addTransactionLogEntry(AccountToAccountDto accountToAccountDto) {
        TransactionEntity receiverTransaction;
        receiverTransaction = modelMapper.map(accountToAccountDto, TransactionEntity.class);
        receiverTransaction.setTransactionTime(LocalDateTime.now());
        TransactionEntity transactionEntity = transactionRepo.save(receiverTransaction);
        logger.debug("Transaction Successful");
        return transactionEntity;
    }

    /**
     * This method talks about adding one entry in transaction log table
     *
     * @param transactionDto
     * @return TransactionEntity
     */
    public TransactionEntity addTransactionLogEntry(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = this.modelMapper.map(transactionDto, TransactionEntity.class);
        transactionEntity.setSenderAccountId("BANK");
        transactionEntity.setTransactionTime(LocalDateTime.now());
        this.transactionRepo.save(transactionEntity);
        logger.debug("Transaction Successful");
        return transactionEntity;
    }

    /**
     * This method talks about adding one entry in deposit log table
     *
     * @param accountToAccountDto
     * @return
     */
    public void addDepositEntry(AccountToAccountDto accountToAccountDto, TransactionEntity transactionEntity, Integer currentBalance) {
        DepositEntity depositEntity = new DepositEntity();
        depositEntity.setAccountNumber(accountToAccountDto.getReceiverAccountId());
        depositEntity.setAmount(accountToAccountDto.getAmount());
        depositEntity.setTransaction_log_id(transactionEntity.getTransactionId());
        depositEntity.setCurrentBalance(currentBalance);
        depositRepo.save(depositEntity);
    }

    /**
     * This method talks about adding one entry in deposit log table
     *
     * @param transactionDto
     * @return
     */
    public void addDepositEntry(TransactionDto transactionDto, TransactionEntity transactionEntity, Integer currentBalance) {
        DepositEntity depositEntity = new DepositEntity();
        depositEntity.setAccountNumber(transactionDto.getReceiverAccountId());
        depositEntity.setAmount(transactionDto.getAmount());
        depositEntity.setTransaction_log_id(transactionEntity.getTransactionId());
        depositEntity.setCurrentBalance(currentBalance);
        depositRepo.save(depositEntity);
    }

    /**
     * This method talks about adding one entry in withdraw log table
     *
     * @param accountToAccountDto
     * @return
     */
    public void addWithdrawEntry(AccountToAccountDto accountToAccountDto, TransactionEntity transactionEntity, Integer currentBalance) {
        withdrawEntity withdrawEntity = new withdrawEntity();
        withdrawEntity.setAccountNumber(accountToAccountDto.getSenderAccountId());
        withdrawEntity.setAmount(accountToAccountDto.getAmount());
        withdrawEntity.setTransaction_log_id(transactionEntity.getTransactionId());
        withdrawEntity.setCurrentBalance(currentBalance);
        withdrawRepo.save(withdrawEntity);
    }

    /**
     * This method talks about adding one entry in withdraw log table
     *
     * @param transactionDto
     * @return
     */
    public void addWithdrawEntry(TransactionDto transactionDto, TransactionEntity transactionEntity, Integer currentBalance) {
        withdrawEntity withdrawEntity = new withdrawEntity();
        withdrawEntity.setAccountNumber(transactionDto.getReceiverAccountId());
        withdrawEntity.setAmount(transactionDto.getAmount());
        withdrawEntity.setTransaction_log_id(transactionEntity.getTransactionId());
        withdrawEntity.setCurrentBalance(currentBalance);
        withdrawRepo.save(withdrawEntity);
    }
}
