package com.epay.controller;

import com.epay.entity.Organization;
import com.epay.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization createdOrganization = organizationService.createOrganization(organization);
        return ResponseEntity.ok(createdOrganization);
    }

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        List<Organization> organizationList = organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizationList);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long organizationId) {
        return organizationService.getOrganizationById(organizationId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{organizatonId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long organizationId, @RequestBody Organization updatedOrganization){
        try{
            Organization organization = organizationService.updateOrganization(organizationId,updatedOrganization);
            return ResponseEntity.ok(organization);
        }catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long organizationId){
       try{
           organizationService.deleteOrganization(organizationId);
           return ResponseEntity.noContent().build();
       }catch (RuntimeException re){
           return ResponseEntity.notFound().build();
       }
    }
}
