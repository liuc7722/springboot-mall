package com.example.demo.dto;

import com.example.demo.constant.ProductCategory;

import lombok.Data;

// 查詢商品的所有條件
@Data
public class ProductQueryParams {
    ProductCategory category;
    String search;
    String orderBy;
    String sort;
    Integer limit;
    Integer offset;
}
