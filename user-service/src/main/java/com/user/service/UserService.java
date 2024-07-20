package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // User Registration
    User registerUser(UserDto userDTO);

    // Get User by ID
    Optional<User> getUserById(String userId);

    // Get User by Email
    Optional<User> getUserByEmail(String email);

    // Get All Users
    List<User> getAllUsers();

    // Update User Profile
    User updateUserProfile(String userId, UserDto userDTO);

    // Update Password
    boolean updatePassword(String userId, String oldPassword, String newPassword);

    // Activate User Account
    boolean activateUserAccount(String userId);

    // Deactivate User Account
    boolean deactivateUserAccount(String userId);

    // Verify Email
    void verifyEmail(String token);

    // Delete User
    boolean deleteUser(String userId);

    // Last Login Update
    void updateLastLogin(String userId);

    // Update Account Status
    boolean updateAccountStatus(String userId, User.Status status);


}
