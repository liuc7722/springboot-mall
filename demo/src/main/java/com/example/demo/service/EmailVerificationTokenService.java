package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.EmailVerificationTokenDao;
import com.example.demo.model.EmailVerificationToken;

@Component
public class EmailVerificationTokenService {

    @Autowired
    EmailVerificationTokenDao tokenDao;

    @Autowired
    UserService userService;

    // 儲存token
    public void saveTokenForUser(String verificationToken, Integer userId) {
        tokenDao.saveTokenForUser(verificationToken, userId);
    }

    // 驗證token
    public boolean verifyToken(String verificationToken) {
        EmailVerificationToken token = tokenDao.getToken(verificationToken);
        if (token != null) {
            // 將 java.util.Date 轉換為 LocalDateTime
            LocalDateTime tokenExpiration = new java.sql.Timestamp(
                    token.getExpirationDate().getTime()).toLocalDateTime();
            if (tokenExpiration.isAfter(LocalDateTime.now())) {
                // 更新用户的 emailVerified 狀態
                userService.setEmailVerified(token.getUserId());
                // 可以選擇刪除或標記該token
                return true;
            }
        }
        return false;
    }
    
    // 刪除舊token
    public void deleteOldTokens(Integer userId){
        tokenDao.deleteOldTokens(userId);
    } 

}
