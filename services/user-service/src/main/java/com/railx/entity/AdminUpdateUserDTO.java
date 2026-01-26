package com.railx.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminUpdateUserDTO {

    private String role;   // USER / ADMIN
    private String status; // ACTIVE / SUSPENDED / DELETED
}
