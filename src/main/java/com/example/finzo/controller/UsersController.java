package com.example.finzo.controller;

import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserDto;
import com.example.finzo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finzo/users")
public class UsersController {
    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<UserEntity> createUser(
            @Valid
            @RequestBody UserDto userDto) {
        UserEntity userEntity = userService.createUser(userDto);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
