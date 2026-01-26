package com.railx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="otp_verification")
public class OTPVerificationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    private String otp;
    private LocalDateTime expiryTime;
    private int attempts;
    private boolean verified;
    private LocalDateTime createdAt;
}
