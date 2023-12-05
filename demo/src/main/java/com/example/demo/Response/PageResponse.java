package com.example.demo.Response;

import com.example.demo.util.Page;

import lombok.Data;

@Data
public class PageResponse<T> extends BaseResponse{

    Page<T> data;
    public PageResponse(int code, String message, Page<T> data) {
        super(code, message);
        this.data = data;
    }
    
}
