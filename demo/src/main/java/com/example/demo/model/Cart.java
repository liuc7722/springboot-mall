package com.example.demo.model;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Cart {
    Integer cartId;
    Integer userId;
    Integer productId;

}
