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

    // 創建用戶並回傳用戶id，若失敗回傳null
    public Integer createUser(@Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = null;
        try {
            connect();
            String sql = "INSERT INTO user (username, password, email, user_role, email_verified)\n" + //
                    "VALUES (?, ?, ?, ?, ?);\n" + //
                    "";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 要取得自動增減值請都加上這句
            pstmt.setString(1, userRegisterRequest.getUsername());
            pstmt.setString(2, userRegisterRequest.getPassword());
            pstmt.setString(3, userRegisterRequest.getEmail());
            pstmt.setString(4, userRegisterRequest.getUserRole());
            pstmt.setInt(5, userRegisterRequest.getEmailVerified());

            int affectedRows = pstmt.executeUpdate(); // 增、改、刪使用Update，會回傳int；查用Query，會回傳ResultSet

            // 若新增成功，回傳ID
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys(); // 取得主鍵值
                if (rs.next()) {
                    userId = rs.getInt(1); // 假設user_id是第一列(那rs是什麼呢?)
                }
            }
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException e) {
            System.out.println("createUser問題");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    // 使用id查詢用戶，查無則回傳null
    public User getUserById(Integer userId) {
        User user = null;
        try {
            connect();
            String sql = "SELECT * FROM user WHERE user_id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery(); // 執行查詢

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setGoogleId(rs.getInt("google_id"));
                user.setFacebookId(rs.getInt("facebook_id"));
                user.setLineId(rs.getInt("line_id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserRole(rs.getString("user_role"));
                user.setEmailVerified(rs.getString("email_verified"));
                user.setCreatedDate(rs.getDate("created_date"));
            }
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
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
    public User getUserByUsername(String username) {
        User user = null;
        try {
            connect();
            String sql = "SELECT * FROM user WHERE username = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserRole(rs.getString("user_role"));
                user.setEmailVerified(rs.getString("email_verified"));
                user.setCreatedDate(rs.getDate("created_date"));
            }
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("getUserByUsername問題");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    // 刪除帳戶
    public void deleteUserById(Integer userId) {
        try {
            connect();
            String sql = "DELETE FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 將使用者的email驗證改為true
    public void setEmailVerified(Integer userId) {
        try {
            connect();
            String sql = "UPDATE user SET email_verified = 1 WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 刪除此人在購物車的紀錄(假定用戶只能購買全部購物車內的商品)
    public void remove(Integer userId) {
        try {
            connect();
            String sql = "DELETE FROM cart WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
