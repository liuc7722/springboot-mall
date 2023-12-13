package com.example.demo.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.model.EmailVerificationToken;

import jakarta.validation.constraints.Email;

@Component
public class EmailVerificationTokenDao extends BaseDao {

    // 儲存email驗證的token
    public void saveTokenForUser(String verificationToken, Integer userId) {
        try {
            connect();
            String sql = "INSERT INTO email_verification_tokens (user_id, verification_token, expiration_date) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, verificationToken);
            LocalDateTime expirationDate = LocalDateTime.now().plusHours(24); // 24小時候過期
            pstmt.setTimestamp(3, Timestamp.valueOf(expirationDate)); // 轉換為 Timestamp
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 根據token取得訊息
    public EmailVerificationToken getToken(String verificationToken) {
        EmailVerificationToken token = null;

        try {
            connect();
            String sql = "SELECT * FROM email_verification_tokens WHERE verification_token = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, verificationToken);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                token = new EmailVerificationToken();
                token.setTokenId(rs.getInt("token_id"));
                token.setUserId(rs.getInt("user_id"));
                token.setVerificationToken(rs.getString("verification_token"));
                token.setExpirationDate(new Date(rs.getTimestamp("expiration_date").getTime()));
                // 设置其他字段...
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception: " + e.getMessage());
        }
        return token;

    }

    // 刪除舊token
    public void deleteOldTokens(Integer userId) {
        try {
            connect();
            String sql = "DELETE FROM email_verification_tokens WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception: " + e.getMessage());
        } 
    }

}
