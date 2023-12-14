package com.example.demo.dto;

import lombok.Data;

// 目前是驗證line pay的支付
@Data
public class VerifyPaymentRequest {
    String transactionId;
    Integer amount;
}
