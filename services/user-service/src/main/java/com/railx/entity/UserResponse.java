package com.railx.entity;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponse {

    private UUID userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String status;
    private String role;
}
