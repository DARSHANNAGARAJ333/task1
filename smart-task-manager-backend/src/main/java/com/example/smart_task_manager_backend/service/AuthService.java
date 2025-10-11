package com.example.smart_task_manager_backend.service;

import com.example.smart_task_manager_backend.dto.UserRegisterDTO;
import com.example.smart_task_manager_backend.model.User;
import com.example.smart_task_manager_backend.repository.UserRepository;
import com.example.smart_task_manager_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Email already exists!";
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setVerified(false);
        user.setActive(true);
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        userRepository.save(user);
        sendVerificationEmail(user.getEmail(), token);

        return "Registration successful! Please check your email to verify your account.";
    }

    private void sendVerificationEmail(String email, String token) {
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Smart Task Manager - Email Verification");
        mailMessage.setText("Click the link to verify your account:\n" + verificationLink);

        mailSender.send(mailMessage);
    }

    public String verifyUser(String token) {
        Optional<User> userOpt = userRepository.findByVerificationToken(token);
        if (userOpt.isEmpty()) return "Invalid token";

        User user = userOpt.get();
        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return "Email verified successfully! You can now log in.";
    }

    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return "Invalid credentials";

        User user = userOpt.get();
        if (!user.isVerified()) return "Please verify your email first.";

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return "Invalid password";
        }

        // ✅ Generate JWT with roles
        return jwtUtil.generateToken(user.getEmail(), user.getRoles());
    }
}
