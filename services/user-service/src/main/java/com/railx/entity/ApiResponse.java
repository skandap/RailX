package com.railx.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApiResponse<T> implements Serializable {

    private boolean success;
    private String message;
    private T data;
}
