package com.example.demo.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Cart {
    Integer cartId;
    Integer userId;
    Date createdDate;
}
