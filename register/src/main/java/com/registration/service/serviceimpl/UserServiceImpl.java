package com.registration.service.serviceimpl;

import com.registration.dto.UserDto;
import com.registration.entity.User;
import com.registration.entity.VerificationToken;
import com.registration.mapper.UserMapper;
import com.registration.repository.UserRepository;
import com.registration.repository.VerificationTokenRepository;
import com.registration.service.EmailService;
import com.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDto userDTO) {

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent()){
            throw new IllegalArgumentException("Email is already registered.");
        }
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
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
        String verificationUrl = "http://localhost:8080/api/register/verify?token=" + verificationCode;

        // Send verification email
        String emailContent = "Please verify your email by clicking the following link: " + verificationUrl;
        emailService.sendVerificationEmail(user.getEmail(), "Email Verification", emailContent);
        return "Verification code sent successfully";
    }

    @Override
    public void verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Invalid verification token")
        );

        if (verificationToken.getExpiryDate().isBefore((LocalDateTime.now()))){
            tokenRepository.delete(verificationToken);
            throw new RuntimeException("Verification token has expired,Try send again.");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
    }

    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with id : " +userId)
                );
    }

}
