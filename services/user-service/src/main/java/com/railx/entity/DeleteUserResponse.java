package com.railx.entity;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeleteUserResponse implements Serializable {

    private UUID userId;
    private String status;

}
