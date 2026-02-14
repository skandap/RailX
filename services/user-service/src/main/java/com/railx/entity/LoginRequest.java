package com.railx.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginRequest {

    @NotBlank
    public String email;
    @NotBlank
    public String password;
}
