package com.example.finzo.controller;

import com.example.finzo.config.JwtHelper;
import com.example.finzo.entity.JwtRequest;
import com.example.finzo.entity.JwtResponse;
import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserDto;
import com.example.finzo.service.UserService;
import com.example.finzo.serviceImpl.UserImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finzo/auth")
public class UsersController {
    @Autowired
    UserService userService;
    @Autowired
    private UserImpl userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @PostMapping("/addUser")
    public ResponseEntity<UserEntity> createUser(
            @Valid
            @RequestBody UserDto userDto) {
        UserEntity userEntity = userService.createUser(userDto);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
