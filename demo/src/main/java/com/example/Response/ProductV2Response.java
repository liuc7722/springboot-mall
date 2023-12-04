package com.example.Response;

import com.example.demo.model.ProductPage;

import lombok.Data;

@Data
public class ProductV2Response extends BaseResponse{
    ProductPage data; // 商品+數量

    public ProductV2Response(int code, String message, ProductPage data) {
        super(code, message);
        
        this.data = data;
    }
    
}
