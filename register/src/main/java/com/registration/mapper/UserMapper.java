package com.registration.mapper;

import com.registration.dto.UserDto;
import com.registration.entity.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(null); // ID will be generated by the database
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword()); // Note: Password encoding should be handled in the service layer
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmailVerified(false);
        user.setStatus(User.Status.INACTIVE);
        user.setLastLogin(null);
        return user;
    }

    public static UserDto toDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole("USER");
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setEmailVerified(user.getEmailVerified());
        userDTO.setStatus(user.getStatus().name());
        userDTO.setLastLogin(user.getLastLogin());
        return userDTO;
    }
}
