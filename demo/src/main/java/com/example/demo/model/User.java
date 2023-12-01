package com.example.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class User {
    Integer userId;
    String userName;
    
    @JsonIgnore // 待測試，當創建User時會回傳User給前端，此時前端並不會收到password的資訊
    String password;
    String email;
    String userRole;
    String emailVerified;
    Date createDate;
    // Date lastModifiedDate;
}
