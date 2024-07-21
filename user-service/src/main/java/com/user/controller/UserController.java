package com.user.controller;

import com.user.dto.UserDto;
import com.user.entity.PasswordUpdateRequest;
import com.user.entity.User;
import com.user.service.UserService;
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

    //required fields for rigistering the user
//    {
//        "firstName":"",
//            "lastName":"",
//            "email":"",
//            "password":"",
//            "phoneNumber":""
//    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDTO) {
        User user = userService.registerUser(userDTO);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/sendcode/{userId}")
    public ResponseEntity<String> sendVerificationCode(@PathVariable Long userId){
        return new ResponseEntity<>(userService.sendVerification(userId),HttpStatus.OK);
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

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

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
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUserProfile(userId, userDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping("/password/{userId}")
    public String updatePassword(@PathVariable Long userId, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        return userService.updatePassword(userId, passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword());
    }

    @PostMapping("/deactivate/{userId}")
    public String deactivateUserAccount(@PathVariable Long userId) {
        return userService.deactivateUserAccount(userId);
    }
}
