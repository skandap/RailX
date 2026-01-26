package com.railx.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
}
