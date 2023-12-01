package com.example.demo.model;

import lombok.Data;

@Data
public class CountdownResponse extends BaseResponse{
    String data;

    public CountdownResponse(int code, String message, String data) {
        super(code, message);
        this.data = data;
    }
    
}
