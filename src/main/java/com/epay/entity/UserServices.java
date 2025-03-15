package com.epay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user_services")
public class UserServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_id",nullable = false)
    private Service service;
    @Column(name="premium_amount",nullable = false)
    private Double premiumAmount;
}
