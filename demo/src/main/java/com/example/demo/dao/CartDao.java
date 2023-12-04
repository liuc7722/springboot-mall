package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.example.demo.model.Cart;

@Component
public class CartDao extends BaseDao {

    // 藉由使用者ID取得購物車
    public Cart getCartByUserId(Integer userId) {
        Cart cart = null;
        try {
            connect();
            String sql = "SELECT * FROM cart WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedDate(rs.getDate("created_date"));
            }
            pstmt.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return cart;
    }

    // 新增一個購物車並回傳cartid
    public Integer createCart(Integer userId) {
        Integer cartId = null;
        try {
            connect();
            String sql = "INSERT INTO cart(user_id, created_date) VALUES (?,?,NOW())";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();

            // 若新增成功，回傳cartid
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys(); // 取得主鍵值
                if (rs.next()) {
                    cartId = rs.getInt(1); 
                }
            }
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return cartId;
    }

    // 添加一個購物車細項
    public void createCartItem(Integer cartId, Integer productId) {
        try {
            connect();
            String sql = "INSERT INTO cart(cart_id, product_id, quantity) VALUES (?,?,1)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e){
            e.getMessage();
        }
    }

}
