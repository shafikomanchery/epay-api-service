package com.epay.dto;

import com.epay.enums.TransactionStatus;

import java.util.Date;

public class TransactionDTO {
    private Long id;
    private Long userServiceId;
    private Double amountPaid;
    private TransactionStatus status;
    private Date transactionDate;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(Long userServiceId) {
        this.userServiceId = userServiceId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
