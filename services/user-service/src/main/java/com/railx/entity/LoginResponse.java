package com.railx.entity;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginResponse implements Serializable {

    private UUID userId;
    private String name;
    private String email;
    private String role;
    private String status;
    private boolean otpRequired;

}