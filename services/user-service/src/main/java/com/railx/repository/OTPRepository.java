package com.railx.repository;

import com.railx.entity.OTPVerificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OTPRepository extends JpaRepository<OTPVerificationDto, Long> {
    Optional<OTPVerificationDto>  findTopByUserIdOrderByCreatedAtDesc(UUID userId);
}
