package com.example.demo.model;

import java.util.Date;

import com.example.demo.constant.ProductCategory;

import lombok.Data;

@Data
public class Product {
    int productId; // 有空改成productId
    String photoUrl;
    String title;
    String description;
    int price;
    String storeUrl;
    String storeName;
    Date createdDate;
    Date lastModifiedDate;
    int stock;
    ProductCategory category;
}

    