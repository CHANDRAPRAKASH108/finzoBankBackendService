package com.example.finzo.controller;

import com.example.finzo.Exception.ApiResponse;
import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.payloads.AccountToAccountDto;
import com.example.finzo.payloads.TransactionDto;
import com.example.finzo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finzo/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    /**
     * @param transactionDto
     * @return Api is to make deposit to an account
     */

    @PostMapping("/deposit")
    public ResponseEntity<TransactionEntity> depositToAccount(
            @Valid
            @RequestBody TransactionDto transactionDto) {
        TransactionEntity currentBalance = this.transactionService.depositToAccount(transactionDto);
        return new ResponseEntity<>(currentBalance, HttpStatus.ACCEPTED);
    }

    /**
     * @param transactionDto
     * @return Aoi is to make withdrawal from an account
     */

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionEntity> withdrawFromAccount(
            @Valid
            @RequestBody TransactionDto transactionDto) {
        TransactionEntity message = this.transactionService.withdrawFromAccount(transactionDto);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    /**
     * @param accountToAccountDto
     * @return Api call to make account to account transactions
     */
    @PostMapping("/accountToAccount")
    public ResponseEntity<TransactionEntity> depositFromAccountToAccount(
            @Valid
            @RequestBody AccountToAccountDto accountToAccountDto) {
        TransactionEntity transactionDto = this.transactionService.depositAccountToAccount(accountToAccountDto);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }
}
