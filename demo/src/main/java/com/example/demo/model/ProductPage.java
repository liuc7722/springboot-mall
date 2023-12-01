package com.example.demo.model;

import java.util.ArrayList;

import lombok.Data;



// 回傳總商品資訊 (商品+數量)
@Data 
public class ProductPage {
    ArrayList<Product> products;
    int total;
}
