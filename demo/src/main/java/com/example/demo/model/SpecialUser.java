package com.example.demo.model;

import lombok.Data;

// 專門處理第三方登入的用戶資料
@Data
public class SpecialUser {
    String id; // 由第三方提供的唯一特殊碼
    String name;
    String email;
}
