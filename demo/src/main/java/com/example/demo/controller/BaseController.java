package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.bean.MySqlConfigBean;

public class BaseController {
    @Autowired
    protected MySqlConfigBean mySqlConfigBean;

    protected Connection conn = null;         //連接資料庫用
    protected Statement stmt = null;          //下sql語法用
    protected ResultSet usernameRs = null;    //取得username結果
    protected ResultSet passwordRs = null;
    protected ResultSet rs= null;
    protected PreparedStatement pstmt = null;

    protected void connect() throws ClassNotFoundException, SQLException{
        //1. 註冊驅動程式
        Class.forName(mySqlConfigBean.getMysqlDriverName());
        //2. 連接資料庫    
        conn = DriverManager.getConnection(mySqlConfigBean.getMysqlUrl() + 
        "?user=" + mySqlConfigBean.getMysqlUsername() + "&password=" + mySqlConfigBean.getMysqlPassword());
    }
}
