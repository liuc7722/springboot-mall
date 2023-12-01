package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // //設定回應的文字編碼(否則會看到亂碼)
        // response.setContentType("text/html;charset=UTF-8");
        
        // //取得可以回應資料給前端的物件
        // PrintWriter pw = response.getWriter();

        // //輸出資料給前端
        // pw.print("<!DOCTYPE html>");
        // pw.print("<html>");
        // pw.print("<head><title>我的Servlet</title></head>");
        // pw.print("<body><h1>Hello Servlet</h1></body>");
        // pw.print("</html>");

        // System.out.println("Hello World!");
        List<Data> dataList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "springboot");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM user")) {
            
            while (rs.next()) {
                Data data = new Data(rs.getInt("id"), rs.getString("name"));
                dataList.add(data);
            }

            for(Data d : dataList){
                System.out.println(d.getid());
                System.out.println(d.getname());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("dataList", dataList);
        request.getRequestDispatcher("/data.jsp").forward(request, response);

    }
}