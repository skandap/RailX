package com.railx.controller;


import com.railx.entity.*;
import com.railx.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    @Autowired
    public UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createEntry(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserResponse user = userService.register(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Admin created successfully. Please login.", user));
    }

    @GetMapping("login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/verify/userId/{userId}/otp/{otp}")
    public ResponseEntity<VerificationResponse> verifyOtp(@PathVariable @Valid @NotNull UUID userId, @PathVariable @Valid @NotNull String otp) {
        return new ResponseEntity<>(userService.verifyOtp(userId, otp), HttpStatus.OK);
    }

    //offset-> how many entries to skip, limit-> how many entries to be displayed
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> fetchAllData(@RequestParam(required = false, defaultValue = "5") int pageSize,
                                                                        @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                                        @RequestParam(required = false,defaultValue = "name") String sortBy,
                                                                        @RequestParam(required = false,defaultValue = "ASC") String sortDir,
                                                                        @RequestParam(required = false) String search) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("ASC")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Details fetched successfully.", userService.fetchAllUsers(PageRequest.of(pageNumber - 1, pageSize, sort),search)), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteEntity(@PathVariable @Valid @NotNull UUID userId, @RequestBody AdminUpdateUserDTO updateUserDTO) {
        return new ResponseEntity<>(new ApiResponse<>(true, "Updated successfully", userService.updateUserRoleStatus(userId, updateUserDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<DeleteUserResponse>> deleteEntity(@PathVariable @Valid @NotNull UUID userId) {
        return new ResponseEntity<>(new ApiResponse<>(true, "Deleted successfully", userService.deleteUser(userId)), HttpStatus.OK);
    }
}
