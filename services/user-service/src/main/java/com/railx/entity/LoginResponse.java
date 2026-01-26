package com.railx.entity;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginResponse {

    private UUID userId;
    private String name;
    private String email;
    private String role;
    private String status;
    private boolean otpRequired;

}