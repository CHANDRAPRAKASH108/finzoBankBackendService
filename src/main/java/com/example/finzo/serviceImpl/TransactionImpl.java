package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.*;
import com.example.finzo.Repository.DepositRepo;
import com.example.finzo.Repository.TransactionRepo;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.Repository.WithdrawRepo;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;
import com.example.finzo.service.TransactionService;
import com.example.finzo.utils.AccountStatus;
import com.example.finzo.utils.CommonMethods;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.finzo.utils.Constants.*;

@Service
public class TransactionImpl implements TransactionService {
    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CommonMethods commonMethods;
    @Autowired
    DepositRepo depositRepo;
    @Autowired
    WithdrawRepo withdrawRepo;

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    /**
     * This method handles cash deposit request from bank branch. This method will be mainly used by bank employees
     *
     * @param transactionDto
     * @return Transaction Entity
     */
    @Override
    public TransactionEntity depositToAccount(TransactionDto transactionDto) {
        String accountNumber = transactionDto.getReceiverAccountId();
        UserAccountEntity userAccountEntity = commonMethods.fetchUserAccount(accountNumber);
        int beforeDeposit = userAccountEntity.getBalance();
        int afterDeposit = beforeDeposit + transactionDto.getAmount();
        try {
            userAccountEntity.setBalance(afterDeposit);
            userAccountRepo.save(userAccountEntity);
            TransactionEntity transactionEntity = commonMethods.addTransactionLogEntry(transactionDto);
            commonMethods.addDepositEntry(transactionDto, transactionEntity, afterDeposit);
            return transactionEntity;
        } catch (Exception e) {
            throw new GlobalExceptionHandler(e.getMessage());
        }
    }

    /**
     * This method handles cash withdraw request from bank branch. This method will be mainly used by bank employees
     *
     * @param transactionDto
     * @return Transaction Entity
     */
    @Override
    public TransactionEntity withdrawFromAccount(TransactionDto transactionDto) {
        int afterWithdrawal = 0;
        UserAccountEntity userAccountEntity = commonMethods.fetchUserAccount(transactionDto.getReceiverAccountId());
        int beforeWithdrawal = userAccountEntity.getBalance();
        try {
            if (beforeWithdrawal >= transactionDto.getAmount()) {
                if (userAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
                    afterWithdrawal = beforeWithdrawal - transactionDto.getAmount();
                    userAccountEntity.setBalance(afterWithdrawal);
                    userAccountRepo.save(userAccountEntity);
                    TransactionEntity transactionEntity = commonMethods.addTransactionLogEntry(transactionDto);
                    commonMethods.addWithdrawEntry(transactionDto, transactionEntity, afterWithdrawal);
                    return transactionEntity;
                } else {
                    throw new AccountStatusException();
                }
            } else {
                throw new NotSufficientBalance(String.valueOf(beforeWithdrawal));
            }
        } catch (Exception e) {
            throw new GlobalExceptionHandler(e.getMessage());
        }
    }

    /**
     * This method is about making a payment from account to account.Method will need one object of accounttoaccountDTO,
     * and it will firstly whether sender account has sufficient balance to send money to receiver account
     * and then will do rest of the transaction process by updating balances and transaction log and by adding
     * entries to deposit log and withdraw log table
     **/

    @Override
    public TransactionEntity depositAccountToAccount(AccountToAccountDto accountToAccountDto) {
        if (commonMethods.validateAccounts(accountToAccountDto)) {
            UserAccountEntity senderAccountEntity = commonMethods.fetchUserAccount(accountToAccountDto.getSenderAccountId());
            UserAccountEntity receiverAccountEntity = commonMethods.fetchUserAccount(accountToAccountDto.getReceiverAccountId());
            if (senderAccountEntity.getBalance() >= accountToAccountDto.getAmount()) {
                if (senderAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
                    try {
                        /**
                         * Updating receivers account balance
                         */
                        receiverAccountEntity.setBalance(receiverAccountEntity.getBalance() + accountToAccountDto.getAmount());
                        userAccountRepo.save(receiverAccountEntity);
                        /**
                         * Updating senders account balance
                         */
                        senderAccountEntity.setBalance(senderAccountEntity.getBalance() - accountToAccountDto.getAmount());
                        userAccountRepo.save(senderAccountEntity);

                        /**
                         * Adding entry for transaction_log table, deposit_log table and withdraw_log table
                         */
                        TransactionEntity transactionEntity = commonMethods.addTransactionLogEntry(accountToAccountDto);

                        commonMethods.addDepositEntry(accountToAccountDto, transactionEntity,
                                receiverAccountEntity.getBalance() + accountToAccountDto.getAmount());

                        commonMethods.addWithdrawEntry(accountToAccountDto, transactionEntity,
                                senderAccountEntity.getBalance() - accountToAccountDto.getAmount());

                        return transactionEntity;
                    } catch (Exception e) {
                        logger.debug(e.getMessage());
                        throw new ResourceNotFoundException(e.getMessage());
                    }

                } else {
                    logger.debug(ACCOUNT_DISABLED);
                    throw new ApiResponse(ACCOUNT_DISABLED);
                }
            } else {
                logger.debug(NOT_SUFFICIENT);
                throw new ApiResponse(NOT_SUFFICIENT + senderAccountEntity.getBalance());
            }
        } else {
            logger.debug(ACCOUNT_SAME);
            throw new ApiResponse(ACCOUNT_SAME);
        }
    }

}
