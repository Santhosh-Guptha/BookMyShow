package com.registration.service;

import com.registration.dto.UserDto;
import com.registration.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // User Registration
    User registerUser(UserDto userDTO);

    String sendVerification(String email);

    // Verify Email
    void verifyEmail(String token);
}
