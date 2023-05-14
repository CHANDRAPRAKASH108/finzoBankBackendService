package com.example.finzo.serviceImpl;

import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.payloads.UserAccountDto;
import com.example.finzo.service.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountImpl implements UserAccountService {
    @Autowired
    UserAccountRepo userAccountRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserAccountDto createAccount(UserAccountDto userAccountDto) {
        UserAccountEntity userAccountEntity = this.modelMapper.map(userAccountDto, UserAccountEntity.class);
        userAccountEntity.setStatus("Active");
        UserAccountEntity createAccount = this.userAccountRepo.save(userAccountEntity);
        return this.modelMapper.map(createAccount, UserAccountDto.class);
    }

    @Override
    public List<UserAccountDto> fetchAllUserAccount() {
        List<UserAccountEntity> userAccountEntityList = this.userAccountRepo.findAll();
        return userAccountEntityList.stream().map(userAccountEntity -> this.modelMapper.map(userAccountEntity, UserAccountDto.class)).toList();
    }

    @Override
    public UserAccountDto fetchUserAccount(Integer account) {
        Optional<UserAccountEntity> userAccountEntity = this.userAccountRepo.findById(account);
        return this.modelMapper.map(userAccountEntity, UserAccountDto.class);
    }
}
