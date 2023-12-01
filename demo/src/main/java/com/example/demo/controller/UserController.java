package com.example.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BaseResponse;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.dto.UserLoginRequest;
import com.example.dto.UserRegisterRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*") // 允許不同網域的網頁呼叫API
public class UserController{


    @Autowired
    private UserService userService;

    // 新增用戶
    @PostMapping("user/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        // 創建用戶並回傳ID
        Integer userId = userService.register(userRegisterRequest);
        if(userId == null)
            return new ResponseEntity<>(new LoginResponse(1, "註冊失敗", null), HttpStatus.OK);
        // 使用此ID查詢用戶並回傳完整資訊
        User user = userService.getUserById(userId);
        // user.setPassword(null); // 刻意把密碼回傳null
        return new ResponseEntity<>(new LoginResponse(0, "註冊成功", user), HttpStatus.OK);
        
    }

    // 登入後回傳用戶資訊
    @PostMapping("user/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);
        
        
        if(user == null)
            return new ResponseEntity<>(new LoginResponse(1, "登錄失敗", user), HttpStatus.OK);
        else    
            return new ResponseEntity<>(new LoginResponse(0, "登錄成功", user), HttpStatus.OK);
    }

    // 刪除帳號
    // 忘記密碼

    /*
     * -----------------------------------------------------------------------------
     */
}
