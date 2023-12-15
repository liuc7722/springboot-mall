package com.example.demo.util;

import jakarta.servlet.http.HttpSession;

// 處理會話驗證
public class SessionUtils {
    
    public static boolean isLoggedIn(HttpSession session){
        return session != null && session.getAttribute("userId") != null;
    }
}
