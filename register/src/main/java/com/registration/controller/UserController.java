package com.registration.controller;

import com.registration.dto.UserDto;
import com.registration.dto.UserLogin;
import com.registration.entity.User;
import com.registration.exception.EmailNotFoundException;
import com.registration.repository.UserRepository;
import com.registration.service.JwtService;
import com.registration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    //required fields for registering the user
//    {
//        "firstName":"",
//            "lastName":"",
//            "email":"",
//            "password":"",
//            "phoneNumber":""
//    }







    @Operation(summary = "User Registration", description = "This API will helps the user to register.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDTO) {
        User user = userService.registerUser(userDTO);
        return ResponseEntity.ok(user);
    }



    @Operation(summary = "Send verification code", description = "Sends a verification code to the user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification code sent successfully"),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    @GetMapping("/sendcode/{email}")
    public ResponseEntity<String> sendVerificationCode(@PathVariable String email){
        return new ResponseEntity<>(userService.sendVerification(email),HttpStatus.OK);
    }




    @Operation(summary = "Verify email", description = "Verifies the user's email with the provided token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token")
    })
    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmail(token);
            return "Email verified successfully!";
        } catch (Exception e) {
            return "Failed to verify email: " + e.getMessage();
        }
    }



    @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public String login(@RequestBody UserLogin request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new EmailNotFoundException("Email not registered")
        );
        if(user.getEmailVerified().equals(true)){
            if(authentication.isAuthenticated()){
                String result = jwtService.generateToken();
                return result;
            }else {
                throw new UsernameNotFoundException("Invalid Username or Password");
            }
        }else {
            return "Please verify you email.";
        }

    }

}
