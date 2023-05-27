package com.example.finzo.controller;

import com.example.finzo.entity.TransactionEntity;
import com.example.finzo.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finzo/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @GetMapping("/fetchByAccount/{accountNumber}")
    public ResponseEntity<String> fetchBalanceByAccountNumber(@PathVariable String accountNumber) {
        Integer currentBalance = this.balanceService.fetchBalanceByAccountNumber(accountNumber);
        return new ResponseEntity<>("Current Balance: " + currentBalance, HttpStatus.OK);
    }

    @GetMapping("/fetchByAadhar/{aadharNumber}")
    public ResponseEntity<String> fetchBalanceByAadharNumber(@PathVariable String aadharNumber) {
        Integer currentBalance = this.balanceService.fetchBalanceByAadharNumber(aadharNumber);
        return new ResponseEntity<>("Current Balance: " + currentBalance, HttpStatus.OK);
    }

    @GetMapping("/allTransactions/{accountNumber}")
    public ResponseEntity<List<TransactionEntity>> fetchAllTransaction(@PathVariable String accountNumber) {
        List<TransactionEntity> transactionEntityList = this.balanceService.fetchAllTransactions(accountNumber);
        return new ResponseEntity<>(transactionEntityList, HttpStatus.OK);
    }
}
