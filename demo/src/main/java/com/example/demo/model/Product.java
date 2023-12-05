package com.example.demo.model;

import java.util.Date;

import com.example.demo.constant.ProductCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Product {

    @Schema(description = "商品id", example = "1")
    int productId; 
    @Schema(description = "商品照片網址", example = "https://www.xxx.com")
    String photoUrl;
    @Schema(description = "商品名稱")
    String title;
    @Schema(description = "商品描述")
    String description;
    @Schema(description = "商品價格")
    int price;
    @Schema(description = "商品圖片網址")
    String storeUrl;
    @Schema(description = "店家名稱")
    String storeName;
    @Schema(description = "創建日期")
    Date createdDate;
    @Schema(description = "最後修改日期")
    Date lastModifiedDate;
    @Schema(description = "存貨")
    int stock;
    @Schema(description = "商品種類")
    ProductCategory category;
}

    