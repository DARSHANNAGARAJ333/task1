package com.example.smart_task_manager_backend.controller;

import com.example.smart_task_manager_backend.dto.UserLoginDTO;
import com.example.smart_task_manager_backend.dto.UserRegisterDTO;
import com.example.smart_task_manager_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ Registration using DTO
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    // ✅ Email verification
    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        return ResponseEntity.ok(authService.verifyUser(token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO loginDTO) {
        String token = authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
