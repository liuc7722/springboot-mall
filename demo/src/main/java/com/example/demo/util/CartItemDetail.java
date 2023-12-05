package com.example.demo.util;


import lombok.Data;

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
