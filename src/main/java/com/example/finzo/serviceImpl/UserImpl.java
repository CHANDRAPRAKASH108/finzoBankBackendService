package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.UserRepo;
import com.example.finzo.entity.JwtRequest;
import com.example.finzo.entity.JwtResponse;
import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserDto;
import com.example.finzo.service.UserService;
import com.example.finzo.utils.CommonMethods;
import com.example.finzo.utils.UserIdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UserImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;
    UserIdGenerator userIdGenerator;
    @Autowired
    CommonMethods commonMethods;
    @Override
    public UserEntity createUser(UserDto userDto) {
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder().encode(userDto.getPassword()));
        userEntity.setRoleId(userDto.getRoleId());
        userRepo.save(userEntity);
        return userEntity;
    }

    @Override
    public JwtResponse loginUser(JwtRequest jwtRequest) {
        return null;
    }

    public UserEntity loadUserByUserId(String userId){
        return userRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
