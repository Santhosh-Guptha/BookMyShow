package com.registration.service;

import com.registration.dto.UserDto;
import com.registration.entity.User;

import java.util.List;

public interface UserService {

    // User Registration
    User registerUser(UserDto userDTO);

    String sendVerification(Long userId);

    // Verify Email
    void verifyEmail(String token);
}
