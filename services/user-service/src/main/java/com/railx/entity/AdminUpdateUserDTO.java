package com.railx.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminUpdateUserDTO implements Serializable {

    private String role;   // USER / ADMIN
    private String status; // ACTIVE / SUSPENDED / DELETED
}
