package com.example.demo.model;

import lombok.Data;

@Data
public class ProductForm {

    String id;

    String name;

    String imageUrl;

    Integer quantity; // 數量

    Integer price;    // 單價

    
}