package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//專門傳輸用戶的註冊
@Data
public class UserRegisterRequest {

    // @NotBlank
    String userName;

    // @NotBlank
    String password;

    // @NotBlank
    // @Email
    String email;

    // @NotBlank
    String userRole;

    // @NotNull
    Integer emailVerified;
}
