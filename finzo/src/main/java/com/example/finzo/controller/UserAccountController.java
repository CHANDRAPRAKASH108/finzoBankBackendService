package com.example.finzo.controller;

import com.example.finzo.payloads.UserAccountDto;
import com.example.finzo.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finzo")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;

    /**
     * @param userAccountRequest
     * @return UserAccount details which got created
     * This endpoint is to create a new user by providing some basic info of a person
     * This endpoint will be available for user having admin access
     */
    @PostMapping("/userAccount")
    public ResponseEntity<UserAccountDto> createAccount(
            @Valid
            @RequestBody UserAccountDto userAccountRequest) {
        UserAccountDto userAccountDto = userAccountService.createAccount(userAccountRequest);
        return new ResponseEntity<>(userAccountDto, HttpStatus.OK);

    }

    /**
     * @return All User Account
     */
    @GetMapping("/userAccount")
    public ResponseEntity<List<UserAccountDto>> fetchAllAccount() {
        List<UserAccountDto> userAccountDtoList = userAccountService.fetchAllUserAccount();
        return new ResponseEntity<>(userAccountDtoList, HttpStatus.OK);
    }

    /**
     * @param account
     * @return A user account entity matching provided account number
     */
    @GetMapping("/userAccount/{account}")
    public ResponseEntity<UserAccountDto> fetchAccountByAccountId(@PathVariable Integer account) {
        UserAccountDto userAccountDtoList = userAccountService.fetchUserAccountById(account);
        return new ResponseEntity<>(userAccountDtoList, HttpStatus.OK);
    }

    /**
     * @param aadharNumber
     * @return A user account entity matching provided aadhar number
     */
    @GetMapping("/userAccount/aadharNumber/{aadharNumber}")
    public ResponseEntity<UserAccountDto> fetchAccountByAadharNumber(@PathVariable String aadharNumber) {
        UserAccountDto userAccountDto = userAccountService.fetchUserAccountByAadhar(aadharNumber);
        return new ResponseEntity<>(userAccountDto, HttpStatus.OK);
    }

    /**
     *
     * @param accountNumber
     * @return Disables account using account number
     */
    @PostMapping("/userAccount/disableAccount/{accountNumber}")
    public ResponseEntity<String> disableAccount(
            @PathVariable String accountNumber) {
        String userAccountDto = userAccountService.disableAccount(accountNumber);
        return new ResponseEntity<>(userAccountDto, HttpStatus.OK);
    }
}
