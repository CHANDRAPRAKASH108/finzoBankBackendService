package com.example.finzo.Repository;

import com.example.finzo.entity.TransactionHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionHistoryRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getTransactionHistory(String accountId) {
        String jpqlQuery = "SELECT t1.transaction_log_id as transaction_id, " +
                "t1.amount as amount, t1.transactionType as transaction_type, " +
                "t1.currentBalance as current_balance, t1.transaction_time as transaction_time " +
                "FROM DepositEntity t1 WHERE t1.accountNumber = :accountId " +
                "UNION ALL " +
                "SELECT t2.transaction_log_id as transaction_id, " +
                "t2.amount as amount, t2.transactionType as transaction_type, " +
                "t2.currentBalance as current_balance, t2.transaction_time as transaction_time " +
                "FROM WithdrawEntity t2 WHERE t2.accountNumber = :accountId";

        TypedQuery<Object[]> query = entityManager.createQuery(jpqlQuery, Object[].class);
        query.setParameter("accountId", accountId);

        return query.getResultList();
    }
}
