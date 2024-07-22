package com.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateProfile {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 20, message = "First name should not be more than 20 characters")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 20, message = "Last name should not be more than 20 characters")
    private String lastName;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNumber;
    private LocalDateTime updatedAt;
}
