package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Response.BaseResponse;
import com.example.Response.CountdownResponse;
import com.example.demo.model.Countdown;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.ObjectName;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "*")
@RestController
public class CountdownController extends BaseController{
    
    // 取得倒數時間
    @GetMapping("/v1/countdown")
    public ResponseEntity getCountdown() {
        Timestamp t = queryDeadline();


        if(t != null){
            // 將Timestamp轉換時間格式給前端用
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String result = s.format(t); 
            return new ResponseEntity<Object>(new CountdownResponse(0, "讀取deadline成功", result), HttpStatus.OK);
        }else{
            return new ResponseEntity<Object>(new CountdownResponse(0, "讀取deadline失敗", ""), HttpStatus.OK);   
        }
    }
    /*
     * code : 0
     * message : 'test'
     * data : "2023-12-12 12:12:00"
     */

    // 修改倒數時間(接收前端的Timestamp(long)，回傳時間格式(String)給前端)
    @PutMapping(value="v1/countdown")
    public ResponseEntity putCountdown(@RequestBody Countdown value) { // 前端API傳的是Timestamp，但其實就是一個很長的
                                                                            // 數字，所以用long接!
        String result = putCountdown(value.getCountdown());                
        if(result.length() == 0){
            return new ResponseEntity<Object>(new BaseResponse(0, "成功"), HttpStatus.OK);
        }else{
            return new ResponseEntity<Object>(new BaseResponse(0, result), HttpStatus.OK);
        }
    }
    

    // 從資料庫取得deadline datatime
    protected Timestamp queryDeadline(){
        try{
            connect();

            String sql = "SELECT * FROM count_down";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);
            rs.next();

            Timestamp t = rs.getTimestamp("deadline"); // table內有一個deadline的欄位，型態為datetime
            rs.close();
            pstmt.close();
            conn.close();
            return t;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected String putCountdown(long t){
        try{
            connect();

            String sql = "UPDATE count_down set deadline = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(t)); // 又來了! 雖然資料庫為datatime格式，但我們可以傳Timestamp，會自動轉型
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            return "";
        }catch(SQLException e){
            return e.getMessage();
        }catch(ClassNotFoundException e){
            return "ClassNotFoundException";
        }
    }















    // // 從資料庫取得deadline datetime
    // protected Timestamp queryDeadline(){
    //     try{
    //         connect();

    //         String sql = "SELECT * FROM count_down";
    //         // pstmt = conn.createStatement(); X 給Statement用的
    //         pstmt = conn.prepareStatement(sql);
    //         rs = pstmt.executeQuery(sql);
    //         rs.next();

    //         Date date = rs.getDate("deadline"); // table內有一個deadline的欄位，型態為datetime
    //         rs.close();
    //         pstmt.close();
    //         conn.close();
    //         return date;
    //     }catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }catch(ClassNotFoundException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }
}
