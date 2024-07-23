package com.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonIgnore
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    @Column(unique = true)
    private String phoneNumber;

    @NotNull(message = "Role is mandatory")
    @JsonIgnore
    private String role;

    @JsonIgnore
    @NotNull(message = "Creation date is mandatory")
    private LocalDateTime createdAt;

    @JsonIgnore
    @NotNull(message = "Update date is mandatory")
    private LocalDateTime updatedAt;


    @NotNull(message = "Email verification status is mandatory")
    private Boolean emailVerified = false;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @JsonIgnore
    @NotNull(message = "Last login date is mandatory")
    private LocalDateTime lastLogin;

    // Enum for Status
    public enum Status {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
