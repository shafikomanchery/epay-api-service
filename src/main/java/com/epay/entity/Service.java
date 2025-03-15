package com.epay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false, length = 255)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name="organization_id", nullable = false)
    private Organization organization;
}
