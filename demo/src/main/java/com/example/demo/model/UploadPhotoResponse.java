package com.example.demo.model;

import com.example.demo.Response.BaseResponse;

import lombok.Data;

@Data
public class UploadPhotoResponse extends BaseResponse{
    String data; // 存放上傳後的路徑+檔名給前端

    public UploadPhotoResponse(int code, String message, String data) {
        super(code, message);
        
        this.data = data;
    }
    // 老樣子，老師喜歡response 1. code(用數字代表成功或失敗) 2. message(文字訊息) 3. (附帶資料) 給前端
    
}
