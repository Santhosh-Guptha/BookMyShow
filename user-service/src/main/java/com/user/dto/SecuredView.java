package com.user.dto;

import com.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class SecuredView {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Boolean emailVerified;

    private User.Status status;

    // Enum for Status
    public enum Status {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
