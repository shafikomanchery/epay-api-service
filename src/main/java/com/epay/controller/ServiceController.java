package com.epay.controller;

import com.epay.entity.Service;
import com.epay.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services") // Base path for all service-related APIs
public class ServiceController {

    private final ServiceService serviceService;

    // Constructor injection
    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // Create a new service
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        Service createdService = serviceService.createService(service);
        return ResponseEntity.ok(createdService);
    }

    // Get all services
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    // Get a service by ID
    @GetMapping("/{serviceId}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long serviceId) {
        return serviceService.getServiceById(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a service
    @PutMapping("/{serviceId}")
    public ResponseEntity<Service> updateService(@PathVariable Long serviceId, @RequestBody Service updatedService) {
        try {
            Service service = serviceService.updateService(serviceId, updatedService);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a service
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        try {
            serviceService.deleteServiceById(serviceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all services by organization ID
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Service>> getServicesByOrganizationId(@PathVariable Long organizationId) {
        List<Service> services = serviceService.getServicesByOrganizationId(organizationId);
        return ResponseEntity.ok(services);
    }
}
