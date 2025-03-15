package com.epay.controller;

import com.epay.dto.UserServicesDTO;
import com.epay.service.UserServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-services") // Base path for user-service-related APIs
public class UserServicesController {

    private final UserServicesService userServicesService;

    // Constructor injection
    @Autowired
    public UserServicesController(UserServicesService userServicesService) {
        this.userServicesService = userServicesService;
    }

    // Create a new user-service subscription
    @PostMapping
    public ResponseEntity<UserServicesDTO> createUserService(
            @RequestParam Long userId,
            @RequestParam Long serviceId,
            @RequestParam Double premiumAmount) {
        UserServicesDTO createdSubscription = userServicesService.createUserService(userId, serviceId, premiumAmount);
        return ResponseEntity.ok(createdSubscription);
    }

    // Get all user-service subscriptions
    @GetMapping
    public ResponseEntity<List<UserServicesDTO>> getAllUserServices() {
        List<UserServicesDTO> subscriptions = userServicesService.getAllUserServicesAsDTOs();
        return ResponseEntity.ok(subscriptions);
    }

    // Get user-service subscriptions by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserServicesDTO>> getUserServicesByUserId(@PathVariable Long userId) {
        List<UserServicesDTO> subscriptions = userServicesService.getUserServicesByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get user-service subscriptions by service ID
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<UserServicesDTO>> getUserServicesByServiceId(@PathVariable Long serviceId) {
        List<UserServicesDTO> subscriptions = userServicesService.getUserServicesByServiceId(serviceId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get a specific user-service subscription by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserServicesDTO> getUserServiceById(@PathVariable Long id) {
        UserServicesDTO subscription = userServicesService.getUserServiceByIdAsDTO(id)
                .orElseThrow(() -> new RuntimeException("Subscription with ID " + id + " not found."));
        return ResponseEntity.ok(subscription);
    }

    // Update the premium amount of a user-service subscription
    @PutMapping("/{id}")
    public ResponseEntity<UserServicesDTO> updateUserService(
            @PathVariable Long id,
            @RequestParam Double newPremiumAmount) {
        UserServicesDTO updatedSubscription = userServicesService.updateUserService(id, newPremiumAmount);
        return ResponseEntity.ok(updatedSubscription);
    }

    // Delete a user-service subscription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserService(@PathVariable Long id) {
        userServicesService.deleteUserService(id);
        return ResponseEntity.noContent().build();
    }
}
