package com.example.finzo.Repository;

import com.example.finzo.entity.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DepositRepo extends JpaRepository<DepositEntity,UUID> {
}
