package com.railx.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginRequest implements Serializable {

    @NotBlank
    public String email;
    @NotBlank
    public String password;
}
