package com.epay.service;

import com.epay.dto.TransactionDTO;
import com.epay.entity.Transaction;
import com.epay.entity.UserServices;
import com.epay.enums.TransactionStatus;
import com.epay.mapper.TransactionMapper;
import com.epay.repository.TransactionRepository;
import com.epay.repository.UserServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserServicesRepository userServicesRepository;

    public TransactionService(TransactionRepository transactionRepository,UserServicesRepository userServicesRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userServicesRepository = userServicesRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionDTO createTransaction(Long userServiceId, Double amountPaid, TransactionStatus status) {
        logger.info("Transaction started with userServiceId:{} amount:{} status:{}", userServiceId,amountPaid, status);
        UserServices userServices = userServicesRepository.findById(userServiceId).orElseThrow(() -> new RuntimeException("User service ID "+ userServiceId +" not found."));
        Transaction transaction = new Transaction();
        transaction.setUserService(userServices);
        transaction.setAmountPaid(amountPaid);
        transaction.setStatus(status);
        transaction.setTransactionDate(new Date()); // Set current date
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    public List<TransactionDTO> getAllTransactionsAsDTOs() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByUserServiceId(Long userServiceId) {
        return transactionRepository.findByUserServiceId(userServiceId).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserServiceUserUserId(userId).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByServiceId(Long serviceId) {
        return transactionRepository.findByUserServiceServiceServiceId(serviceId).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO updateTransactionStatus(Long transactionId, TransactionStatus newStatus) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    transaction.setStatus(newStatus);
                    return transactionMapper.toDTO(transactionRepository.save(transaction));
                })
                .orElseThrow(() -> new RuntimeException("Transaction with ID " + transactionId + " not found."));
    }

    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new RuntimeException("Transaction with ID " + transactionId + " not found.");
        }
        transactionRepository.deleteById(transactionId);
    }
}
