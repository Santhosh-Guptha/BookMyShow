package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;

import java.util.List;

public interface UserService {

    // Get User by ID
    User getUserById(Long userId);

    String sendVerification(Long userId);

    // Get User by Email
    User getUserByEmail(String email);

    // Get All Users
    List<User> getAllUsers();

    // Update User Profile
    User updateUserProfile(Long userId, UserDto userDTO);

    // Update Password
    String updatePassword(String email, String oldPassword, String newPassword);

    // Deactivate User Account
    String deactivateUserAccount(Long userId);

    // Verify Email
    void verifyEmail(String token);

    // Delete User
    void deleteUser(Long userId);

    // Last Login Update
    void updateLastLogin(String userId);

    // Update Account Status
    boolean updateAccountStatus(String userId, User.Status status);


}
