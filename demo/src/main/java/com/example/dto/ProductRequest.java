package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// 專門處理前端新增商品時傳來的資訊
@Data
public class ProductRequest {

    @NotNull
    String photoUrl;
    @NotNull
    String title;
    String description;
    @NotNull
    int price;
    @NotNull
    String storeUrl;
    @NotNull
    String storeName;
    @NotNull
    int stock;
    @NotNull
    String category; // 商品種類可以使用enum呈現，這邊先不做
}
