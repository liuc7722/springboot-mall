package com.example.demo.model;

import lombok.Data;

@Data
public class SingleProductResponse extends BaseResponse{

    Product product;

    public SingleProductResponse(int code, String message, Product product) {
        super(code, message);
        this.product = product;
    }
    
}
