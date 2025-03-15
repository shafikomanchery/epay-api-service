package com.epay.entity;

import com.epay.enums.TenantRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(length = 50)
    private String phone;
    @ManyToOne
    @JoinColumn(name="organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TenantRole role = TenantRole.MANAGER;

}
