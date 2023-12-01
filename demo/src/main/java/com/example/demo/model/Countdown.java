package com.example.demo.model;

import java.security.Timestamp;
import java.sql.Date;

import lombok.Data;


// 前後端傳輸日期的class，處理countdown
@Data
public class Countdown {  
    long countdown; // 為何用long呢? 
}
