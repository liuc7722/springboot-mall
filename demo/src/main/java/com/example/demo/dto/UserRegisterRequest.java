package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//專門用戶的註冊
@Data
public class UserRegisterRequest {

    @Schema(description = "使用者帳號")
    @NotBlank
    String username;

    @Schema(description = "使用者密碼")
    @NotBlank
    String password;

    @Schema(description = "電子信箱", example = "xxx@gmail.com")
    @NotBlank
    @Email
    String email;

    @Schema(description = "使用者角色")
    @NotBlank
    String userRole;

    @Schema(description = "是否驗證電子郵件")
    @NotNull
    Integer emailVerified;
}
