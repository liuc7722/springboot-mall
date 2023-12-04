package com.example.demo.dto;

import lombok.Data;

// 查詢訂單列表的所有條件
@Data
public class OrderQueryParams {
    Integer userId;
    Integer limit;
    Integer offset;
}
