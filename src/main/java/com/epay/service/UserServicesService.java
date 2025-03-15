package com.epay.service;

import com.epay.dto.UserServicesDTO;
import com.epay.entity.User;
import com.epay.entity.UserServices;
import com.epay.mapper.UserServicesMapper;
import com.epay.repository.ServiceRepository;
import com.epay.repository.UserRepository;
import com.epay.repository.UserServicesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicesService {

    private final UserServicesRepository userServicesRepository;
    private final UserServicesMapper userServicesMapper;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public UserServicesService(UserServicesRepository userServicesRepository,UserRepository userRepository,ServiceRepository serviceRepository, UserServicesMapper userServicesMapper) {
        this.userServicesRepository = userServicesRepository;
        this.userRepository = userRepository;
        this.serviceRepository=serviceRepository;
        this.userServicesMapper = userServicesMapper;
    }

    // Create a new user-service subscription
    public UserServicesDTO createUserService(Long userId, Long serviceId, Double premiumAmount) {
        // Business logic to validate userId and serviceId, etc., before saving
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with ID "+userId+" not found."));
        com.epay.entity.Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service with ID "+serviceId+" not found."));
        UserServices userService = new UserServices();
        userService.setUser(user);
        userService.setService(service);
        userService.setPremiumAmount(premiumAmount);
        UserServices savedSubscription = userServicesRepository.save(userService);
        return userServicesMapper.toDTO(savedSubscription);
    }

    // Retrieve all user-service subscriptions as DTOs
    public List<UserServicesDTO> getAllUserServicesAsDTOs() {
        return userServicesRepository.findAll().stream()
                .map(userServicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Retrieve subscriptions by user ID
    public List<UserServicesDTO> getUserServicesByUserId(Long userId) {
        return userServicesRepository.findByUserUserId(userId).stream()
                .map(userServicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Retrieve subscriptions by service ID
    public List<UserServicesDTO> getUserServicesByServiceId(Long serviceId) {
        return userServicesRepository.findByServiceServiceId(serviceId).stream()
                .map(userServicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Retrieve a subscription by ID as a DTO
    public Optional<UserServicesDTO> getUserServiceByIdAsDTO(Long id) {
        return userServicesRepository.findById(id).map(userServicesMapper::toDTO);
    }

    // Update subscription premium amount
    public UserServicesDTO updateUserService(Long id, Double newPremiumAmount) {
        return userServicesRepository.findById(id).map(userService -> {
            userService.setPremiumAmount(newPremiumAmount);
            return userServicesMapper.toDTO(userServicesRepository.save(userService));
        }).orElseThrow(() -> new RuntimeException("Subscription with ID " + id + " not found."));
    }

    // Delete a subscription by ID
    public void deleteUserService(Long id) {
        if (!userServicesRepository.existsById(id)) {
            throw new RuntimeException("Subscription with ID " + id + " not found.");
        }
        userServicesRepository.deleteById(id);
    }
}
