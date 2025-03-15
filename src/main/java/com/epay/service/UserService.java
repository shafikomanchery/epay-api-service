package com.epay.service;

import com.epay.dto.UserDTO;
import com.epay.entity.User;
import com.epay.enums.UserRole;
import com.epay.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Hash password using mobile number as default
    public String hashPasswordUsingMobile(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.isEmpty()) {
            throw new IllegalArgumentException("Mobile number cannot be null or empty");
        }
        // Hash the mobile number
        return passwordEncoder.encode(mobileNumber);
    }

    // Hash password
    public String hashPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        // Hash the newPassword
        return passwordEncoder.encode(newPassword);
    }

    // Create a new user
    public User createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        // Hash the mobile number as the default password
        String defaultPasswordHash = hashPasswordUsingMobile(user.getPhone());
        user.setPasswordHash(defaultPasswordHash);
        // Save the User entity
        return userRepository.save(user);
    }

    // Retrieve all users as DTOs
    public List<UserDTO> getAllUsersAsDTOs() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update an existing user
    public User updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
            user.setRole(UserRole.valueOf(userDTO.getUserRole()));
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Convert User entity to UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setUserRole(user.getRole().toString());
        return userDTO;
    }

    // Convert UserDTO to User entity
    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setRole(UserRole.valueOf(userDTO.getUserRole()));
        return user;
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found."));
        // Hash the new password
        String hashedPassword = hashPassword(newPassword);
        user.setPasswordHash(hashedPassword);
        // Save the updated user
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
