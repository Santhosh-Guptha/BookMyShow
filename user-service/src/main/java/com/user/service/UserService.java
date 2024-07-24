package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;

import java.util.List;

public interface UserService {

    // Get User by ID
    User getUserById(Long userId);

    String sendVerification(String email);

    // Get User by Email
    User getUserByEmail(String email);

    // Get All Users
    List<User> getAllUsers();

    // Update User Profile
    String updateUserProfile(String email, UserDto userDTO);

    // Update Password
    String updatePassword(String email, String oldPassword, String newPassword);

    // Deactivate User Account
    String deactivateUserAccount(String email);

    String activateUserAccount(String email);

    // Verify Email
    void verifyEmail(String token);

    // Delete User
    void deleteUser(String email);

    // Last Login Update
    void updateLastLogin(String userId);

    // Update Account Status
    boolean updateAccountStatus(String userId, User.Status status);

    String sendPasswordReset(String email);

    void resetPassword(String token, String newPassword);

//    void forgotPassword(String email);

}
