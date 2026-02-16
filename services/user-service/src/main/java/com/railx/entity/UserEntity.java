package com.railx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String  status;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
