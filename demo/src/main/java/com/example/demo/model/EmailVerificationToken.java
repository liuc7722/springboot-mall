package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmailVerificationToken {
    Integer tokenId;
    Integer userId;
    String verificationToken;
    Date expirationDate;
}
