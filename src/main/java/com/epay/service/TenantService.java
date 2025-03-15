package com.epay.service;

import com.epay.entity.Organization;
import com.epay.entity.Tenant;
import com.epay.repository.OrganizationRepository;
import com.epay.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final OrganizationRepository organizationRepository;

    public TenantService(TenantRepository tenantRepository, OrganizationRepository organizationRepository) {
        this.tenantRepository = tenantRepository;
        this.organizationRepository = organizationRepository;
    }

    // Add a new tenant
    public Tenant createTenant(Long organizationId, Tenant tenant) {
        // Ensure the organization exists
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization with ID " + organizationId + " not found"));

        // Associate tenant with the organization
        tenant.setOrganization(organization);
        return tenantRepository.save(tenant);
    }

    // Retrieve all tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // Retrieve tenants by organization
    public List<Tenant> getTenantsByOrganizationId(Long organizationId) {
        return tenantRepository.findByOrganizationOrganizationId(organizationId);
    }

    // Get a tenant by ID
    public Optional<Tenant> getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId);
    }

    // Update tenant details
    public Tenant updateTenant(Long tenantId, Tenant updatedTenant) {
        return tenantRepository.findById(tenantId).map(tenant -> {
            tenant.setName(updatedTenant.getName());
            tenant.setEmail(updatedTenant.getEmail());
            tenant.setPhone(updatedTenant.getPhone());
            tenant.setRole(updatedTenant.getRole());
            return tenantRepository.save(tenant);
        }).orElseThrow(() -> new RuntimeException("Tenant with ID " + tenantId + " not found"));
    }

    // Delete a tenant
    public void deleteTenant(Long tenantId) {
        if (tenantRepository.existsById(tenantId)) {
            tenantRepository.deleteById(tenantId);
        } else {
            throw new RuntimeException("Tenant with ID " + tenantId + " not found");
        }
    }
}
