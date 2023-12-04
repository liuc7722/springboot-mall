package com.example.demo.model;

import lombok.Data;

@Data
public class CartItem {
    Integer cartItemId;
    Integer cartId;
    Integer productId;
    Integer quantity;
}
