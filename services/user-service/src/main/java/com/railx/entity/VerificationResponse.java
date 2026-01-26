package com.railx.entity;


import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class VerificationResponse {
    private UUID userId;
    private String verified;
    private String status;
}
