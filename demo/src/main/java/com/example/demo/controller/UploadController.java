package com.example.demo.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.UploadPhotoResponse;



//上傳檔案的API
@RestController
public class UploadController {
    @Value("${upload.server.path}")
    private String serverUploadPath;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files){ 
        // 取得檔案後只是先存到伺服器(程式?)上，我們需要把它存至磁碟機內

        // 取得上傳的原始檔名
        String filename = files[0].getOriginalFilename();
        System.out.println("上傳: " + filename);

        // 設定儲存的路徑
        String finalPath = new File(serverUploadPath + filename).getAbsolutePath();
        System.out.println(finalPath);
        
        try {
            // 儲存檔案
            files[0].transferTo(new File(finalPath)); // 此時就確實把檔案從記憶體存放到磁碟機了
        } catch (IllegalStateException e) {
            return new ResponseEntity<Object>(new UploadPhotoResponse(1, "上傳失敗", e.getMessage()), HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<Object>(new UploadPhotoResponse(2, "上傳失敗", e.getMessage()), HttpStatus.OK);
        }
        // return new ResponseEntity<Object>(new BaseResponseModel(0, finalPath), HttpStatus.OK);
        return new ResponseEntity<Object>(new UploadPhotoResponse(0, "上傳成功", "　" + filename), HttpStatus.OK);
        
    }
}
