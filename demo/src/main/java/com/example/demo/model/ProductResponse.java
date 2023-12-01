package com.example.demo.model;

import java.util.ArrayList;

import lombok.Data;


// 回傳多個商品的資料格式
@Data
public class ProductResponse extends BaseResponse{
    ArrayList<Product> data;

    public ProductResponse(int code, String message, ArrayList<Product> data) {
        super(code, message);
        
        this.data = data;
    }
    
}
