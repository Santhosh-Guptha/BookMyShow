package com.user.service.serviceimpl;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.entity.VerificationToken;
import com.user.mapper.UserMapper;
import com.user.repository.UserRepository;
import com.user.repository.VerificationTokenRepository;
import com.user.service.EmailService;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public User registerUser(UserDto userDTO) {

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent()){
            throw new IllegalArgumentException("Email is already registered.");
        }
        User user = UserMapper.toEntity(userDTO);
        user = userRepository.save(user);

//        String verificationCode = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setUser(user);
//        verificationToken.setToken(verificationCode);
//        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
//        tokenRepository.save(verificationToken);
//
//        // Create verification URL
//        //change the URL depending on the controller
//        String verificationUrl = "http://localhost:8080/api/users/verify?token=" + verificationCode;
//
//        // Send verification email
//        String emailContent = "Please verify your email by clicking the following link: " + verificationUrl;
//        emailService.sendVerificationEmail(user.getEmail(), "Email Verification", emailContent);
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with id : " +userId)
                );
    }

    @Override
    public String sendVerification(Long userId) {
        String verificationCode = UUID.randomUUID().toString();
        User user = getUserById(userId);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(verificationCode);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(verificationToken);

        // Create verification URL
        //change the URL depending on the controller
        String verificationUrl = "http://localhost:8080/api/users/verify?token=" + verificationCode;

        // Send verification email
        String emailContent = "Please verify your email by clicking the following link: " + verificationUrl;
        emailService.sendVerificationEmail(user.getEmail(), "Email Verification", emailContent);
        return "Verification code sent successfully";
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User not found with email : " + email)
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserProfile(Long userId, UserDto userDto) {
        User existingUser = getUserById(userId);
        if (existingUser.getEmail().equals(userDto.getEmail())){
            existingUser.setEmail(userDto.getEmail());
        }else {
            existingUser.setEmail(userDto.getEmail());
            existingUser.setEmailVerified(Boolean.FALSE);
        }
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    @Override
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean activateUserAccount(String userId) {
        return false;
    }

    @Override
    public boolean deactivateUserAccount(String userId) {
        return false;
    }

    @Override
    public void verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Invalid verification token")
        );

        if (verificationToken.getExpiryDate().isBefore((LocalDateTime.now()))){
            throw new RuntimeException("Verification token has expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(String.valueOf(userId))) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(String.valueOf(userId));
    }

    @Override
    public void updateLastLogin(String userId) {

    }

    @Override
    public boolean updateAccountStatus(String userId, User.Status status) {
        return false;
    }
}
