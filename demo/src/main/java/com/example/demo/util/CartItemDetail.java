package com.example.demo.util;


import lombok.Data;

// 查詢購物車時回傳給前端的資料結構
@Data
public class CartItemDetail {
    Integer cartId;
    Integer userId;
    Integer productId;
    Integer quantity;
    Integer price;
    String title;
    String photoUrl;
}
