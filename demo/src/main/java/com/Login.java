`package com;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


class LoginResult{
    public LoginResult(String account, String password){
        this.account = account;
        this.password = password;
    }
    String account;
    String password;
}
//API的response資料結構
class ActionResult{
    int code;        //狀態碼
    String message;  //訊息描述

    LoginResult data; //登入後預計回傳的資料

    public ActionResult(int code, String mgssage, LoginResult data){
        this.code = code;
        this.message = mgssage;
        this.data = data;
    }
}
public class Login extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        //接收前端網頁傳來的username和password參數
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        printAccountsByUsername();
    
        
        //登入
        boolean isSuccess = login(username, password);
        // 1登入成功，0登入失敗
        ActionResult actionResult = new ActionResult(isSuccess?1:0, isSuccess?"成功!":"失敗", new LoginResult(username, password));
        //這行有點醜，怎麼改呢?

        //使用gson(google寫的轉換器)將actionResult(Java物件)轉換成JSON回傳給前端
        String jsonString = new Gson().toJson(actionResult); //問題是它會怎麼轉換呢?
        System.out.println(jsonString);
        
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
    }
    //驗證帳號與密碼是否存在資料庫
    protected boolean login(String username, String password){
        Connection conn = null; //連接資料庫用
        Statement stmt = null;  //下sql語法用
        ResultSet rs = null;    //取得sql結果用
        try{
            //1. 註冊驅動程式
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 連接資料庫
            conn = DriverManager.getConnection("jdbc:mysql://localhost/my_system?user=root&password=springboot");
            //3. 取得statement物件
            stmt = conn.createStatement();
            //4. 查詢資料庫
            rs = stmt.executeQuery("SELECT count(*) as c FROM account a WHERE name ='" + username + "' and code='" + password + "';");
            //現在有個問題，rs是什麼呢?
            
            //取得c欄位的資料
            int count = 0;
            while(rs.next()){
                count = rs.getInt("c");
            }
            rs.close();
            stmt.close();
            conn.close();

            return count != 0;
        }catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException錯誤");
            return false;
        }catch(SQLException e){
            System.out.println("SQL錯誤");
            return false;
        }
    }
    //練習印出code是root的所有資料，(在終端上)
    protected void printAccountsByUsername(){
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // 1. 註冊驅動程式 (对于现代JDBC驱动，这一步通常是可选的)
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 連接資料庫
        conn = DriverManager.getConnection("jdbc:mysql://localhost/my_system?user=root&password=springboot");
        // 3. 创建PreparedStatement
        String sql = "SELECT id, name, phone, code FROM account WHERE code = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "root"); // 这里我们假设你想根据code（即username参数）来过滤结果
        // 4. 执行查询
        rs = pstmt.executeQuery();

        // 5. 遍历结果集
        while(rs.next()){
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            String code = rs.getString("code");
            System.out.println("id: " + id + ", name: " + name + ", phone: " + phone + ", code: " + code);
        }
    } catch (ClassNotFoundException e) {
        System.out.println("ClassNotFoundException错误");
    } catch(SQLException e) {
        System.out.println("SQLException错误: " + e.getMessage());
    } finally {
        // 6. 关闭资源
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException错误: " + e.getMessage());
        }
    }
}

}
//這就是俗稱的API
