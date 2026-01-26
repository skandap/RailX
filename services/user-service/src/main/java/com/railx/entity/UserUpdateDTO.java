package com.railx.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}
