package com.example.demo.Response;

import lombok.Data;

@Data
public class CountdownResponse extends BaseResponse{
    String data; // 日期(之後改名)

    public CountdownResponse(int code, String message, String data) {
        super(code, message);
        this.data = data;
    }
    
}
