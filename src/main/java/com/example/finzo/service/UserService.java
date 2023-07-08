package com.example.finzo.service;

import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserDto;

public interface UserService {
    UserEntity createUser(UserDto userDto);
}
