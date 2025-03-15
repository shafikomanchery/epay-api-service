package com.epay.repository;

import com.epay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserServiceId(Long userServiceId);
    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);
    List<Transaction> findByUserServiceUserUserId(Long userId);
    List<Transaction> findByUserServiceServiceServiceId(Long userId);
}