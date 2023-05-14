package com.example.finzo.controller;

import com.example.finzo.Repository.UserAccountRepo;
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
     * This endpoint is to create a new user by providing some basic info of a person
     * This endpoint will be available for user having admin access
     **/
    @PostMapping("/userAccount")
    public ResponseEntity<UserAccountDto> createAccount(
            @Valid
            @RequestBody UserAccountDto userAccountRequest) {
        UserAccountDto userAccountDto = userAccountService.createAccount(userAccountRequest);
        return new ResponseEntity<>(userAccountDto, HttpStatus.OK);
    }

    @GetMapping("/userAccount")
    public ResponseEntity<List<UserAccountDto>> fetchAllAccount(){
        List<UserAccountDto> userAccountDtoList = userAccountService.fetchAllUserAccount();
        return new ResponseEntity<>(userAccountDtoList, HttpStatus.OK);
    }
    @GetMapping("/userAccount/{account}")
    public ResponseEntity<UserAccountDto> fetchAccountByAccount(@PathVariable Integer account){
        UserAccountDto userAccountDtoList = userAccountService.fetchUserAccount(account);
        return new ResponseEntity<>(userAccountDtoList, HttpStatus.OK);
    }
}
