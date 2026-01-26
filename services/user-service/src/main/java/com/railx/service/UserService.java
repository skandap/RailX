package com.railx.service;

import com.railx.entity.*;

import java.util.List;
import java.util.UUID;

public interface UserService {


    // =======================
    // COMMON / SHARED
    // =======================

    /**
     * Register a new user (public)
     * Status = PENDING_ACTIVATION, Role = USER/ADMIN
     */
    UserResponse register(UserInputDTO userInputDTO);

    /**
     * Verify OTP for user registration
     * Changes status PENDING_ACTIVATION → ACTIVE
     */
    VerificationResponse verifyOtp(UUID userId, String otp);


    // =======================
    // PUBLIC / USER APIs
    // =======================

    /**
     * If first login → OTP required
     */
    LoginResponse login(String email, String password);

    UserResponse fetchOwnUserById(UUID userId);

    /**
     * Update own profile (name, phone)
     * Only ACTIVE users allowed
     */
    Object updateOwnUser(UserUpdateDTO userInputDTO, UUID userId);




    // =======================
    // ADMIN APIs
    // =======================

    List<UserResponse> fetchAllUsers();

    /**
     * Update any user (role & status)
     */
    UserResponse updateUserRoleStatus(UUID userId, AdminUpdateUserDTO updateUserDTO);

    /**
     * Soft delete user (status = DELETED)
     */
    DeleteUserResponse deleteUser(UUID userId);

}


