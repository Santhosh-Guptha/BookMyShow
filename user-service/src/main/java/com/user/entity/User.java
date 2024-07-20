package com.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
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

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull(message = "Creation date is mandatory")
    private LocalDateTime createdAt;

    @NotNull(message = "Update date is mandatory")
    private LocalDateTime updatedAt;

    @NotNull(message = "Email verification status is mandatory")
    private Boolean emailVerified = false;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Last login date is mandatory")
    private LocalDateTime lastLogin;


    //Enum for Role
    public enum Role {
        USER,
        ADMIN
    }

    // Enum for Status
    public enum Status {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
