package com.example.smart_task_manager_backend.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String fullName;
    private String email;
    private String password;
}

