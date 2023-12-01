// package com.example.demo.controller;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.model.SpecialUser;
// import com.example.demo.model.BaseResponse;
// import com.example.demo.model.LoginCredentials;
// import com.example.demo.model.LoginResponse;

// @CrossOrigin(origins = "*") // 允許不同網域的網頁呼叫API
// @RestController
// public class LoginController extends BaseController {

//     // @PostMapping("/login")
//     // public ResponseEntity login(@RequestBody LoginCredentials loginCredentials) {
//     //     // 登入
//     //     Integer result = loginVerity(loginCredentials.getUsername(), loginCredentials.getPassword());

//     //     LoginResponse response = null;
//     //     if (result == 0) { // 帳號不存在
//     //         response = new LoginResponse(0, "帳號不存在", -1);
//     //     } else if (result == 1) { // 密碼錯誤
//     //         response = new LoginResponse(1, "密碼錯誤", -1);
//     //     } else if (result == 2) { // 帳密皆正確
//     //         Integer userId = getUserId(loginCredentials.getUsername(), loginCredentials.getPassword());
//     //         response = new LoginResponse(2, "登入成功", userId);
//     //     }

//     //     return new ResponseEntity<Object>(response, HttpStatus.OK); 
//     // }

//     // 取得用戶的ID
//     private Integer getUserId(String username, String password) {

//         try {
//             connect();

//             String sql = "SELECT id FROM user WHERE username = ? and password = ?";
//             pstmt = conn.prepareStatement(sql);
//             pstmt.setString(1, username);
//             pstmt.setString(2, password);
//             rs = pstmt.executeQuery();

//             rs.next();
//             Integer userId = rs.getInt("id");
//             return userId;
//         } catch (SQLException e) {
//             System.out.println(e.getMessage());
//         } catch (ClassNotFoundException e) {
//             System.out.println(e.getMessage());
//         }
//         return null;
//     }

//     // 使用google登入的API
//     @PostMapping("/v1/googlelogin")
//     public ResponseEntity googleLogin(@RequestBody SpecialUser account) {
//         String result = insertAccount(account);

//         if (result.length() == 0)
//             return new ResponseEntity<>(new BaseResponse(0, "成功"), HttpStatus.OK);
//         else
//             return new ResponseEntity<>(new BaseResponse(1, result), HttpStatus.OK);
//     }

//     // -----------------------------------------------------------

//     // 處理第方三登入用戶註冊的一個函式
//     private String insertAccount(SpecialUser account) {
//         try {
//             connect();
//             // 先判斷資料庫有無此id
//             String sql = "SELECT count(*) as c from account WHERE userid= ? ";
//             pstmt = conn.prepareStatement(sql);
//             pstmt.setString(1, account.getId());
//             rs = pstmt.executeQuery();
//             if (rs.next()) {
//                 int count = rs.getInt("c");
//                 // 帳號已存在
//                 if (count >= 1)
//                     return "";
//             }

//             // 新增此第三方用戶到資料庫
//             sql = "INSERT INTO account (name, phone, code, email, userid) VALUES (?, null, null, ?, ?);\n" + //
//                     "";
//             pstmt = conn.prepareStatement(sql);
//             pstmt.setString(1, account.getName());
//             pstmt.setString(2, account.getEmail());
//             pstmt.setString(3, account.getId());
//             pstmt.executeUpdate();

//             pstmt.close();
//             conn.close();
//             return "";
//         } catch (SQLException e) {
//             return e.getMessage();
//         } catch (ClassNotFoundException e) {
//             return "ClassNotFoundException ";
//         }
//     }

//     // 驗證帳號與密碼是否存在資料庫
//     protected int loginVerity(String username, String password) {
//         try {
//             // 連接資料庫
//             connect();
//             final String QUERY = "SELECT password FROM user WHERE username = ?";
//             pstmt = conn.prepareStatement(QUERY);
//             pstmt.setString(1, username);
//             ResultSet rs = pstmt.executeQuery();
//             if (rs.next()) {
//                 String retrievedPassword = rs.getString("password");
//                 if (password.equals(retrievedPassword)) {
//                     System.out.println("帳密正確!!");
//                     return 2; // 帳密皆正確
//                 } else {
//                     System.out.println("密碼錯誤!!");
//                     return 1; // 密碼錯誤
//                 }
//             } else {
//                 System.out.println("帳號不存在!!");
//                 return 0; // 帳號不存在
//             }
//         } catch (ClassNotFoundException e) {
//             System.out.println("ClassNotFoundException錯誤");
//             return -1;
//         } catch (SQLException e) {
//             System.out.println("SQL錯誤");
//             return -1;
//         }
//     }
// }
