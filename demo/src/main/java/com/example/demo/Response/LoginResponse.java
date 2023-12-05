package com.example.demo.Response;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class LoginResponse extends BaseResponse{
    User user; // 若登入成功，回傳整個User給前端

    public LoginResponse(int code, String message, User user) {
        super(code, message);
        this.user = user;
    }
}
