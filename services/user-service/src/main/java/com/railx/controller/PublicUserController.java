package com.railx.controller;


import com.railx.entity.*;
import com.railx.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class PublicUserController {

    @Autowired
    public UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createEntry(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserResponse user = userService.register(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User created successfully, please login", user));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.CREATED);
    }

    @GetMapping("/verify/userId/{userId}/otp/{otp}")
    public ResponseEntity<VerificationResponse> verifyOtp(@PathVariable @Valid @NotNull UUID userId, @PathVariable @Valid @NotNull String otp) {
        return new ResponseEntity<>(userService.verifyOtp(userId, otp), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> fetchById(@PathVariable @Valid @NotNull UUID userId) {
        return new ResponseEntity<>(new ApiResponse<>(true, "Details fetched successfully.", userService.fetchOwnUserById(userId)), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteEntity(@PathVariable @Valid @NotNull UUID userId, @RequestBody UserUpdateDTO updateUserDTO) {
        userService.updateOwnUser(updateUserDTO, userId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Updated successfully", null), HttpStatus.OK);
    }
}
