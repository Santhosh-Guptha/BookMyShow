package com.registration.controller;

import com.registration.dto.UserDto;
import com.registration.entity.User;
import com.registration.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private UserService userService;

    //required fields for registering the user
//    {
//        "firstName":"",
//            "lastName":"",
//            "email":"",
//            "password":"",
//            "phoneNumber":""
//    }
    @PostMapping
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

}
