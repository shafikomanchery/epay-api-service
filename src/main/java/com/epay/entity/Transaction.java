package com.epay.entity;

import com.epay.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_service_id", nullable = false)
    private UserServices userService;

    @Temporal(TemporalType.DATE)
    @Column(name="transaction_date", nullable = false)
    private Date transactionDate;
    @Column(name="amount_paid", nullable = false)
    private Double amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private TransactionStatus status;
}
