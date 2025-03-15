package com.epay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    @Column(nullable = false,length = 255)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column(length = 255)
    private String contactEmail;
    @Column(length = 50)
    private String contactPhone;
}
