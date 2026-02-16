package com.railx.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateDTO implements Serializable {

    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}
