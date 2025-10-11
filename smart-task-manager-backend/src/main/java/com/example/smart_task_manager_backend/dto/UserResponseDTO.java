package com.example.smart_task_manager_backend.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private boolean enabled;
}
