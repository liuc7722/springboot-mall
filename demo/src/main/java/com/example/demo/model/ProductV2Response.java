package com.example.demo.model;

import lombok.Data;

@Data
public class ProductV2Response extends BaseResponse{
    ProductPage data;

    public ProductV2Response(int code, String message, ProductPage data) {
        super(code, message);
        
        this.data = data;
    }
    
}
