package com.example.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
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
import com.example.demo.service.EmailSendService;
import com.example.demo.service.EmailVerificationTokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
// @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") //
// 允許不同網域的網頁呼叫API
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailSendService sendService;

    @Autowired
    EmailVerificationTokenService verificationTokenService;

    // 新增用戶
    @PostMapping("user/register")
    @Tag(name = "使用者API")
    @Operation(summary = "註冊", description = "輸入註冊資訊")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "打API成功", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "打API失敗", content = @Content),
    })
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        // 創建用戶並回傳ID
        Integer userId = userService.register(userRegisterRequest);

        if (userId == null)
            return new ResponseEntity<>(new LoginResponse(1, "註冊失敗", null, null), HttpStatus.OK);

        // 使用此ID查詢用戶並回傳完整資訊
        User user = userService.getUserById(userId);

        // 創建帳號成功，寄送email給用戶，並創建email驗證token儲存在資料表
        String verificationToken = UUID.randomUUID().toString();
        verificationTokenService.saveTokenForUser(verificationToken, userId);

        String verificationUrl = "http://localhost:5173/verify?token=" + verificationToken;
        String emailBody = "請點擊以下連結驗證您的電子郵件: " + verificationUrl;
        sendEmail(user.getEmail(), "恭喜" + user.getUserName() + "創建帳號成功，請立即完成信箱認證", emailBody);

        // 生成JWT
        String token = JwtUtil.createToken(user.getUserName(), user.getUserId());
        // 創建含有JWT的響應
        LoginResponse response = new LoginResponse(0, "登入成功", user, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 發送郵件
    private void sendEmail(String toEmail, String subject, String body) {
        sendService.sendEmail(toEmail, subject, body);
    }

    // 驗證信箱
    @GetMapping("/user/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        boolean isVerified = verificationTokenService.verifyToken(token);
        if (isVerified) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("無效的驗證連接");
        }
    }

    // 重發驗證信(只要有token就能取得userId，就能取得用戶email並發送信件)
    @PostMapping("/user/resend-verification-email")
    public ResponseEntity<?> resendVerificationEmail(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Integer userId = jwtUtil.getUserIdFromToken(token);

        // 根據用戶ID取得email
        User user = userService.getUserById(userId);
        String email = user.getEmail();

        // 生成新的token並儲存
        String newVerificationToken = UUID.randomUUID().toString();
        verificationTokenService.deleteOldTokens(userId);
        verificationTokenService.saveTokenForUser(newVerificationToken, userId);

        // 發送電子信箱
        String verificationUrl = "http://localhost:5173/verify?token=" + newVerificationToken;
        String emailBody = "請點擊以下連結驗證您的電子郵件: " + verificationUrl;
        sendEmail(email, "電子郵件驗證", emailBody);

        return ResponseEntity.ok().build();
    }

    // 登入後回傳用戶資訊
    @PostMapping("/user/login")
    @Tag(name = "使用者API")
    @Operation(summary = "登入", description = "輸入帳號密碼")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "打API成功", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "打API失敗", content = @Content),
    })
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        User user = userService.login(userLoginRequest);

        if (user == null) {
            return new ResponseEntity<>(new LoginResponse(1, "登錄失敗", user, null), HttpStatus.BAD_REQUEST);
        } else {
            // 生成JWT
            String token = JwtUtil.createToken(user.getUserName(), user.getUserId());
            // 創建含有JWT的response
            LoginResponse response = new LoginResponse(0, "登入成功", user, token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // 取得用戶資訊
    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);
        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
 

    // 刪除帳號
    @DeleteMapping("/user/{userId}")
    @Tag(name = "使用者API")
    @Operation(summary = "刪除帳號", description = "輸入使用者ID")
    public ResponseEntity deleteUser(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 忘記密碼

}
