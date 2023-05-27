package com.example.finzo.Repository;

import com.example.finzo.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccountEntity, Integer> {
    UserAccountEntity findByAadharNumber(String aadharNumber);
}
