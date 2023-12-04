package com.example.demo.util;

import java.util.List;

import lombok.Data;

// 查詢商品列或查詢訂單列時回傳給前端的資料結構，為何放在util呢? 只是一個設計而已
@Data
public class Page<T> {
 
    Integer limit;
    Integer offset;
    Integer total;
    List<T> results;
}
