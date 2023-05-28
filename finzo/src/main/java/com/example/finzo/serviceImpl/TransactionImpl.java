package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ApiResponse;
import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;
import com.example.finzo.service.TransactionService;
import com.example.finzo.utils.AccountStatus;
import com.example.finzo.utils.CommonMethods;
import com.example.finzo.utils.TransactionIdGenerator;
import com.example.finzo.utils.TransactionType;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    CommonMethods commonMethods;

    @Override
    public Integer depositToAccount(TransactionDto transactionDto) {
        String accountNumber = transactionDto.getReceiverAccountId();
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        int beforeDeposit = userAccountEntity.getBalance();
        int afterDeposit = beforeDeposit + transactionDto.getAmount();
        userAccountEntity.setBalance(afterDeposit);
        userAccountRepo.save(userAccountEntity);
        TransactionEntity transactionEntity = this.modelMapper.map(transactionDto, TransactionEntity.class);
        transactionEntity.setTransactionId(TransactionIdGenerator.generateUUID());
        transactionEntity.setTransactionTime(LocalDateTime.now());
        transactionEntity.setReceiverAccountId("BANK");
        transactionEntity.setTransactionType(TransactionType.DEPOSIT);
        this.transactionRepo.save(transactionEntity);
        return afterDeposit;
    }

    @Override
    public String withdrawFromAccount(TransactionDto transactionDto) {
        String accountNumber = transactionDto.getReceiverAccountId();
        int afterWithdrawal = 0;
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        int beforeWithdrawal = userAccountEntity.getBalance();
        if (beforeWithdrawal >= transactionDto.getAmount()) {
            if (userAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
                afterWithdrawal = beforeWithdrawal - transactionDto.getAmount();
                userAccountEntity.setBalance(afterWithdrawal);
                userAccountRepo.save(userAccountEntity);
                TransactionEntity transactionEntity = this.modelMapper.map(transactionDto, TransactionEntity.class);
                transactionEntity.setTransactionId(TransactionIdGenerator.generateUUID());
                transactionEntity.setTransactionTime(LocalDateTime.now());
                transactionEntity.setTransactionType(TransactionType.WITHDRAW);
                transactionEntity.setSenderAccountId("BANK");
                this.transactionRepo.save(transactionEntity);
                return WITHDRAWN_MESSAGE + afterWithdrawal;
            } else {
                return ACCOUNT_DISABLED;
            }
        } else {
            return NOT_SUFFICIENT + beforeWithdrawal;
        }
    }

    @Override
    public TransactionEntity depositAccountToAccount(AccountToAccountDto accountToAccountDto) {
        UserAccountEntity senderAccountEntity = userAccountRepo.findById(accountToAccountDto.getSenderAccountId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+accountToAccountDto.getSenderAccountId()));
        UserAccountEntity receiverAccountEntity = userAccountRepo.findById(accountToAccountDto.getReceiverAccountId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+accountToAccountDto.getReceiverAccountId()));
        TransactionEntity receiverTransaction;
        if (senderAccountEntity.getBalance() >= accountToAccountDto.getAmount()) {
            if (senderAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
                try {
                    Integer fetchReceiverAmount = receiverAccountEntity.getBalance();
                    Integer updateBalance = fetchReceiverAmount + accountToAccountDto.getAmount();
                    receiverAccountEntity.setBalance(updateBalance);
                    userAccountRepo.save(receiverAccountEntity);
                    senderAccountEntity.setBalance(senderAccountEntity.getBalance() - accountToAccountDto.getAmount());
                    userAccountRepo.save(senderAccountEntity);
                    // Receiver transaction
                    receiverTransaction = modelMapper.map(accountToAccountDto, TransactionEntity.class);
                    receiverTransaction.setTransactionType(TransactionType.DEPOSIT);
                    receiverTransaction.setTransactionId(TransactionIdGenerator.generateUUID());
                    receiverTransaction.setTransactionTime(LocalDateTime.now());
                    transactionRepo.save(receiverTransaction);
                    return receiverTransaction;
                } catch (Exception e) {
                    throw new ResourceNotFoundException(e.getMessage());
                }

            } else {
                throw new ApiResponse(ACCOUNT_DISABLED);
            }
        } else {
            throw new ApiResponse(NOT_SUFFICIENT + senderAccountEntity.getBalance());
        }
    }

}
