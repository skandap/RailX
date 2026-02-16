package com.railx.entity;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class VerificationResponse implements Serializable {
    private UUID userId;
    private String verified;
    private String status;
}
