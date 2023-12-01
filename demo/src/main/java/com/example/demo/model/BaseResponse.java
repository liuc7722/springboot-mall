package com.example.demo.model;

import lombok.Data;

//所有API response的父類別
@Data
public class BaseResponse {
    int code;
    String message;

    public BaseResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
}
