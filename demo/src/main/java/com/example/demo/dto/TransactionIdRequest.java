package com.example.demo.dto;

import lombok.Data;

@Data
public class TransactionIdRequest {
    String transactionId;
    Integer orderId;
}
