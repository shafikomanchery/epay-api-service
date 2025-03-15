package com.epay.controller;

import com.epay.dto.PasswordUpdateRequestDTO;
import com.epay.dto.UserDTO;
import com.epay.entity.User;
import com.epay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Base URL for all user-related endpoints
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        // Convert DTO to entity and save user
        User createdUser = userService.createUser(userDTO);
        // Convert entity back to DTO for the response
        UserDTO responseDTO = userService.convertToDTO(createdUser);
        return ResponseEntity.ok(responseDTO);
    }

    // Retrieve all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // Fetch all users, convert entities to DTOs
        List<UserDTO> users = userService.getAllUsersAsDTOs();
        return ResponseEntity.ok(users);
    }

    // Retrieve a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        UserDTO userDTO = userService.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        UserDTO responseDTO = userService.convertToDTO(updatedUser);
        return ResponseEntity.ok(responseDTO);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateRequestDTO passwordUpdateRequest) {
        userService.updatePassword(id, passwordUpdateRequest.getNewPassword());
        return ResponseEntity.ok("Password updated successfully!");
    }
}
