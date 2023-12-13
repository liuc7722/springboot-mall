package com.example.demo.util;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// JWT 工具類
@Component
public class JwtUtil {

    private static long time = 1000 * 60 * 60 * 1;
    private static String sign = "admin";
    
    // 創建JWT
    public static String createToken(String username, Integer userId){
        JwtBuilder jwtBuilder = Jwts.builder();
        // jwtToken -> abc.def.xyz
        String jwtToken = jwtBuilder
            // Header: 頭部
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            // Payload: 載荷
            .claim("username", username)
            // .claim("sub", userId) 等同於底下的setSubject(...)
            .claim("role", "admin")
            .setSubject(String.valueOf(userId))
            .setExpiration(new Date(System.currentTimeMillis() + time)) // Token的過期時間
            .setId(UUID.randomUUID().toString()) // id字段
            // sign: 簽名
            .signWith(SignatureAlgorithm.HS256, sign) // 設置加密算法和簽名
            // 使用"."連接成一個完整的字串
            .compact();

        return jwtToken;
    }

    // 驗證JWT 1. 確保JWT沒有過期 2. 驗證JWT的簽名以確保它沒有被竄改
    public boolean validationToken(String token){
        try {
            Jwts.parser().setSigningKey(sign).parseClaimsJws(token);
        } catch (Exception e) {
            System.out.println("token已過期!");
            return false;
        }
        return true;
    }

    // 從JWT提取用戶ID
    public Integer getUserIdFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(sign).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
