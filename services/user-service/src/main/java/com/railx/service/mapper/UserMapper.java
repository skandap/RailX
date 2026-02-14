package com.railx.service.mapper;

import com.railx.entity.*;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity mapToEntity(UserInputDTO userInputDTO) {
        return UserEntity.builder()
                .name(userInputDTO.getName())
                .email(userInputDTO.getEmail())
                .phoneNumber(userInputDTO.getPhoneNumber())
                .role(userInputDTO.getRole())
                .status(UserStatus.PENDING_ACTIVATION.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public static UserResponse mapToUserData(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .role(userEntity.getRole())
                .status(UserStatus.ACTIVE.name())
                .build();
    }

    public static LoginResponse mapToLogin(UserEntity userEntity) {
        return LoginResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .otpRequired(true)
                .build();
    }
}
