package com.example.finzo.serviceImpl;

import com.example.finzo.Repository.UserRepo;
import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserDto;
import com.example.finzo.service.UserService;
import com.example.finzo.utils.UserIdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;
    UserIdGenerator userIdGenerator;

    @Override
    public UserEntity createUser(UserDto userDto) {
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setRoleId(userDto.getRoleId());
        userRepo.save(userEntity);
        return userEntity;
    }
}
