package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 專門處理登入用
@Data
public class UserLoginRequest {
    
    @NotBlank
    String username;
    @NotBlank
    String password;
}
