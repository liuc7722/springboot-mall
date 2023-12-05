package com.example.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response.BaseResponse;
import com.example.demo.Response.LoginResponse;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // 允許不同網域的網頁呼叫API
public class UserController{


    @Autowired
    private UserService userService;    

    // 新增用戶
    @PostMapping("user/register")
    @Tag(name = "使用者API")
    @Operation(summary = "註冊", description = "輸入註冊資訊")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "打API成功",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = BaseResponse.class))}),
        @ApiResponse(responseCode = "400", description = "打API失敗",
            content = @Content),
    })
    public ResponseEntity register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        // 創建用戶並回傳ID
        Integer userId = userService.register(userRegisterRequest);
        
        if(userId == null)
            return new ResponseEntity<>(new LoginResponse(1, "註冊失敗", null), HttpStatus.OK);
        // 使用此ID查詢用戶並回傳完整資訊
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(new LoginResponse(0, "註冊成功", user), HttpStatus.OK);
        
    }

    // 登入後回傳用戶資訊
    @PostMapping("/user/login")
    @Tag(name = "使用者API")
    @Operation(summary = "登入", description = "輸入帳號密碼")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "打API成功",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = BaseResponse.class))}),
        @ApiResponse(responseCode = "400", description = "打API失敗",
            content = @Content),
    })
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest userLoginRequest,
                                HttpSession session){
        User user = userService.login(userLoginRequest);
        System.out.println("Login Session ID: " + session.getId());
        
        if(user == null){
            session.removeAttribute("userId");
            return new ResponseEntity<>(new LoginResponse(1, "登錄失敗", user), HttpStatus.BAD_REQUEST);
        }else{    
            // 將登入資訊寫入session
            session.setAttribute("userId", user.getUserId());
            return new ResponseEntity<>(new LoginResponse(0, "登錄成功", user), HttpStatus.OK);
        }    
    }

    // 刪除帳號
    @DeleteMapping("/user/{userId}")
    @Tag(name = "使用者API")
    @Operation(summary = "刪除帳號", description = "輸入使用者ID")
    public ResponseEntity deleteUser(@PathVariable Integer userId){
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 忘記密碼

}
