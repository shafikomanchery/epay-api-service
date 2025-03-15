package com.epay.controller;

import com.epay.entity.Tenant;
import com.epay.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants") // Base path for all tenant-related APIs
public class TenantController {

    private final TenantService tenantService;

    // Constructor injection for dependency
    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // Create a new tenant for an organization
    @PostMapping("/organization/{organizationId}")
    public ResponseEntity<Tenant> createTenant(@PathVariable Long organizationId, @RequestBody Tenant tenant) {
        Tenant createdTenant = tenantService.createTenant(organizationId, tenant);
        return ResponseEntity.ok(createdTenant);
    }

    // Retrieve all tenants
    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    // Retrieve tenants by organization ID
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Tenant>> getTenantsByOrganizationId(@PathVariable Long organizationId) {
        List<Tenant> tenants = tenantService.getTenantsByOrganizationId(organizationId);
        return ResponseEntity.ok(tenants);
    }

    // Retrieve a specific tenant by ID
    @GetMapping("/{tenantId}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long tenantId) {
        return tenantService.getTenantById(tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update tenant details
    @PutMapping("/{tenantId}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long tenantId, @RequestBody Tenant updatedTenant) {
        try {
            Tenant tenant = tenantService.updateTenant(tenantId, updatedTenant);
            return ResponseEntity.ok(tenant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a tenant by ID
    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long tenantId) {
        try {
            tenantService.deleteTenant(tenantId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
