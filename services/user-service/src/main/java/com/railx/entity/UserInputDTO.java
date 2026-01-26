package com.railx.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInputDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters and spaces")
    private String name;
    @NotBlank
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be at least 8 characters long and contain letters and digits"
    )
    private String password;
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10–15 digits")
    private String phoneNumber;
    @NotBlank
    private String role;

}
