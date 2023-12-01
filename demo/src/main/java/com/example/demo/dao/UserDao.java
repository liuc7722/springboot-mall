package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

import jakarta.validation.Valid;

@Component
public class UserDao extends BaseDao {

    // 在資料庫新增一筆用戶，回傳用戶的ID，若失敗回傳null
    public Integer createUser(@Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = null;
        try {
            connect();
            String sql = "INSERT INTO user (username, password, email, userrole, emailverified)\n" + //
                    "VALUES (?, ?, ?, ?, ?);\n" + //
                    "";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 要取得自動增減值請都加上這句
            pstmt.setString(1, userRegisterRequest.getUserName());
            pstmt.setString(2, userRegisterRequest.getPassword());
            pstmt.setString(3, userRegisterRequest.getEmail());
            pstmt.setString(4, userRegisterRequest.getUserRole());
            pstmt.setInt(5, userRegisterRequest.getEmailVerified());

            int affectedRows = pstmt.executeUpdate(); // 增、改、刪使用Update，會回傳int；查用Query，會回傳ResultSet

            // 若新增成功，回傳ID
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys(); // 取得主鍵值
                if (rs.next()) {
                    userId = rs.getInt(1); // 假設userID是第一列(那rs是什麼呢?)
                }
            }
            if(rs != null)
                rs.close();
            if(pstmt != null)
                pstmt.close();
            if(conn != null)
                conn.close();

        } catch (SQLException e) {
            System.out.println("createUser問題");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    // 使用userId查詢資料庫的某筆用戶，查無則回傳null
    public User getUserById(Integer userId) {
        User user = null;
        try {
            connect();
            String sql = "SELECT * FROM user WHERE id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery(); // 執行查詢

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("id"));
                user.setGoogleId(rs.getInt("googleid"));
                user.setFacebookId(rs.getInt("facebookid"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserRole(rs.getString("userrole"));
                user.setEmailVerified(rs.getString("emailverified"));
                user.setCreateDate(rs.getDate("createdate"));
            }
            if(rs != null)
                rs.close();
            if(pstmt != null)
                pstmt.close();
            if(conn != null)
                conn.close();

        } catch (SQLException e) {
            System.out.println("getUserById問題");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    // 根據帳號找出用戶，若無回傳null
    public User getUserByUsername(String username){
        User user = null;
        try{
            connect();
            String sql = "SELECT * FROM user WHERE username = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserRole(rs.getString("userrole"));
                user.setEmailVerified(rs.getString("emailverified"));
                user.setCreateDate(rs.getDate("createddate"));
            }
            if(rs != null)
                rs.close();
            if(pstmt != null)
                pstmt.close();
            if(conn != null)
                conn.close();
        }catch(SQLException e){
            System.out.println("getUserByUsername問題");
            System.out.println(e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

}
