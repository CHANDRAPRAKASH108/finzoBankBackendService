package com.example.finzo.Repository;

import com.example.finzo.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findByReceiverAccountId(String receiverAccountId);
}
