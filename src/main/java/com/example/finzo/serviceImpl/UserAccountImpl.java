package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.UserAccountRepo;
import com.example.finzo.Repository.UserRepo;
import com.example.finzo.entity.UserAccountEntity;
import com.example.finzo.entity.UserAccountResponse;
import com.example.finzo.entity.UserEntity;
import com.example.finzo.payloads.UserAccountDto;
import com.example.finzo.service.UserAccountService;
import com.example.finzo.utils.AccountNumberGenerator;
import com.example.finzo.utils.AccountStatus;
import com.example.finzo.utils.CommonMethods;
import com.example.finzo.utils.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.finzo.utils.Constants.ALREADY_DEACTIVATED;
import static com.example.finzo.utils.Constants.DEACTIVATED;

@Service
public class UserAccountImpl implements UserAccountService {
    @Autowired
    UserAccountRepo userAccountRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    CommonMethods cm;
    @Autowired
    PasswordUtils passwordUtils;

    @Override
    public UserAccountResponse createAccount(UserAccountDto userAccountDto) {
        String accountNumber = AccountNumberGenerator.generateUniqueNumber();
        UserAccountEntity userAccountEntity = this.modelMapper.map(userAccountDto, UserAccountEntity.class);
        userAccountEntity.setId(accountNumber);
        userAccountEntity.setStatus(AccountStatus.ACTIVE);
        UserAccountEntity createAccount = this.userAccountRepo.save(userAccountEntity);
        /**
         * Saving record to user repo for above created user
         */
        String userId = cm.generateUserId();
        String bCryptPass = PasswordUtils.bcryptPassword(userAccountDto.getPassword());
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setPassword(bCryptPass);
        user.setAccountNumber(accountNumber);
        user.setRoleId(2);
        userRepo.save(user);
        UserAccountResponse userAccountResponse = this.modelMapper.map(createAccount, UserAccountResponse.class);
        userAccountResponse.setUserId(userId);
        return userAccountResponse;
    }

    @Override
    public List<UserAccountDto> fetchAllUserAccount() {
        List<UserAccountEntity> userAccountEntityList = this.userAccountRepo.findAll();
        return userAccountEntityList.stream().map(userAccountEntity -> this.modelMapper.map(userAccountEntity, UserAccountDto.class)).toList();
    }

    @Override
    public UserAccountDto fetchUserAccountById(Integer account) {
        UserAccountEntity userAccountEntity = this.userAccountRepo.findById(String.valueOf(account))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + account));
        return this.modelMapper.map(userAccountEntity, UserAccountDto.class);
    }

    @Override
    public UserAccountDto fetchUserAccountByAadhar(String aadharNumber) {
        UserAccountEntity userAccountEntity;
        try {
            userAccountEntity = this.userAccountRepo.findByAadharNumber(aadharNumber);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found by aadharNumber :" + aadharNumber);
        }
        return this.modelMapper.map(userAccountEntity, UserAccountDto.class);
    }

    @Override
    public String disableAccount(String accountNumber) {
        UserAccountEntity userAccountEntity = userAccountRepo.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID :" + accountNumber));
        if (userAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
            userAccountEntity.setStatus(AccountStatus.IN_ACTIVE);
            userAccountRepo.save(userAccountEntity);
            return DEACTIVATED;
        } else {
            return ALREADY_DEACTIVATED;
        }
    }
}
