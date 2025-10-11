package com.example.smart_task_manager_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Smart Task Manager - Email Verification";
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

        String body = "Welcome to Smart Task Manager!\n\n"
                + "Please click the link below to verify your account:\n"
                + verificationLink + "\n\n"
                + "If you didn’t register, please ignore this email.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
