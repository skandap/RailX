package com.railx.entity;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeleteUserResponse {

    private UUID userId;
    private String status;

}
