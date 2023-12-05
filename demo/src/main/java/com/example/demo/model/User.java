package com.example.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class User {
    Integer userId;
    Integer googleId;
    Integer facebookId;
    Integer lineId;
    String userName;
    @JsonIgnore // 回傳User給前端時，會省略password
    String password;
    String email;
    String userRole;
    String emailVerified;
    Date createdDate;
    // Date lastModifiedDate;
}
