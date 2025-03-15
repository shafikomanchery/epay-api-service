package com.epay.controller;

import com.epay.dto.TransactionDTO;
import com.epay.enums.TransactionStatus;
import com.epay.service.TransactionService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions") // Base path for transaction-related APIs
public class TransactionController {

    private final TransactionService transactionService;

    // Constructor injection
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(
            @RequestParam @NotNull Long userServiceId,
            @RequestParam @NotNull @Positive Double amountPaid,
            @RequestParam @NotNull TransactionStatus status) {
        TransactionDTO createdTransaction = transactionService.createTransaction(userServiceId, amountPaid, status);
        return ResponseEntity.ok(createdTransaction);
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactionsAsDTOs();
        return ResponseEntity.ok(transactions);
    }

    // Get transactions by user-service subscription ID
    @GetMapping("/user-service/{userServiceId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserServiceId(@PathVariable Long userServiceId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByUserServiceId(userServiceId);
        return ResponseEntity.ok(transactions);
    }

    // Get transactions by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    // Get transactions by service ID
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByServiceId(@PathVariable Long serviceId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByServiceId(serviceId);
        return ResponseEntity.ok(transactions);
    }

    // Get transactions within a date range
    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    // Update transaction status
    @PutMapping("/{transactionId}/status")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestParam TransactionStatus newStatus) {
        TransactionDTO updatedTransaction = transactionService.updateTransactionStatus(transactionId, newStatus);
        return ResponseEntity.ok(updatedTransaction);
    }

    // Delete a transaction by ID
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
