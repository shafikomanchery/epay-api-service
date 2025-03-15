package com.epay.service;

import com.epay.entity.Organization;
import com.epay.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Organization updateOrganization(Long id, Organization updatedOrganization) {
        return organizationRepository.findById(id).map(organization -> {
            organization.setName(updatedOrganization.getName());
            organization.setAddress(updatedOrganization.getAddress());
            organization.setContactEmail(updatedOrganization.getContactEmail());
            organization.setContactPhone(updatedOrganization.getContactPhone());
            return organizationRepository.save(organization);
        }).orElseThrow(() -> new RuntimeException("Organization with ID " + id + " not found."));
    }

    public void deleteOrganization(Long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Organization with ID " + id + " not found.");
        }
    }
}
