package com.epay.entity;

import com.epay.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(length = 50)
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt=new Date();


}
