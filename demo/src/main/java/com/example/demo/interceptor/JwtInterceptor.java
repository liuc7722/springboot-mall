package com.example.demo.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    // 創建一個攔截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        // 從請求中獲取JWT

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validationToken(token)) {
                // JWT驗證成功
                return true;
            } else {
                // 如果token為null或驗證失敗，拒絕訪問
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return false;
            }
        }
        // 如果沒有 Authorization 標頭，可能是公開路由，繼續處理請求
        return true;
    }

}
