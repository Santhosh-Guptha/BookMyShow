package com.registration.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long userId;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 20, message = "First name should not be more than 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 20, message = "Last name should not be more than 20 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email should not be more than 50 characters")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Role is mandatory")
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean emailVerified;
    private String status;
    private LocalDateTime lastLogin;
}
