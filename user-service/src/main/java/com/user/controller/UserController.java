package com.user.controller;

import com.user.dto.ResetPasswordDto;
import com.user.dto.UserDto;
import com.user.dto.PasswordUpdateRequest;
import com.user.entity.User;
import com.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sendcode/{userId}")
    public ResponseEntity<String> sendVerificationCode(@PathVariable String email,String url){
        return new ResponseEntity<>(userService.sendVerification(email),HttpStatus.OK);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmail(token);
            return "Email verified successfully!";
        } catch (Exception e) {
            return "Failed to verify email: " + e.getMessage();
        }
    }





    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }



    @Operation(summary = "Get user by email", description = "Returns a user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }



    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved")
    })
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    //required fields to update
//    {
//        "firstName":"",
//            "lastName":"",
//            "phoneNumber":"",
//            "email":""
//    }



    @Operation(summary = "Update user", description = "Updates the details of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUserProfile(userId, userDto),HttpStatus.OK);
    }



    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable long userId)

    {

        userService.deleteUser(userId);
        return new ResponseEntity<String>("data deleted successfully  :" + userId,HttpStatus.OK);
    }



    @Operation(summary = "Update password", description = "Updates the password of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/password/{email}")
    public String updatePassword(@PathVariable String email, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        return userService.updatePassword(email, passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword());
    }


    @Operation(summary = "Deactivate user account", description = "Deactivates a user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/deactivate/{userId}")
    public String deactivateUserAccount(@PathVariable Long userId) {
        return userService.deactivateUserAccount(userId);
    }

    @PostMapping("/request-password-reset/{email}")
    public ResponseEntity<String> requestPasswordReset(@PathVariable String email) {
        userService.sendPasswordReset(email);
        return ResponseEntity.ok("Password reset email sent successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto request) {
        userService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password has been reset successfully");
    }
}
