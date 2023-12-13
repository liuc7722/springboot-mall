package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CartQueryParams;
import com.example.demo.util.CartItemDetail;

@Component
public class CartDao extends BaseDao {

    // 查看購物車列表(詳細版的商品資訊)
    public List<CartItemDetail> getCartItems(CartQueryParams queryParams) {
        List<CartItemDetail> cartItems = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT c.cart_id, c.user_id, c.product_id, c.quantity, p.price, p.title, p.photo_url" +
                        " FROM cart c JOIN product p ON c.product_id = p.product_id WHERE 1=1");
        // 查詢條件
        addFiltering(sqlBuilder, queryParams);
        // 排序
        // sqlBuilder.append(" ORDER BY order_date DESC"); // 預設就是日期，不可改
        // 分頁
        sqlBuilder.append(" LIMIT ").append(queryParams.getLimit()).append(" OFFSET ")
                .append(queryParams.getOffset());

        try {
            connect();
            pstmt = conn.prepareStatement(sqlBuilder.toString());
            // 設置查詢參數
            pstmt.setInt(1, queryParams.getUserId());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                CartItemDetail item = new CartItemDetail();
                item.setCartId(rs.getInt("cart_id"));
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getInt("price"));
                item.setTitle(rs.getString("title"));
                item.setPhotoUrl(rs.getString("photo_url"));
                cartItems.add(item);
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL getCart錯誤");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        return cartItems;
    }

    // 檢查購物車是否已有此商品
    public boolean CheckIfItemExists(Integer userId, Integer productId) {
        boolean exists = false;
        try {
            connect();
            String sql = "SELECT count(*) as c FROM cart WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("c") > 0)
                    exists = true;
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    // 添加或修改一件商品到購物車
    public void addOrUpdateCart(Integer userId, Integer productId, boolean exists) {
        try {
            connect();
            if (exists) { // 若商品已存在購物車
                String sql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    // 处理未找到记录或未能更新的情况
                    System.out.println("未找到对应的购物车项或更新失败");
                }
            } else {
                String sql = "INSERT INTO cart(user_id, product_id, quantity, created_date) VALUES (?, ?, 1, NOW())";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    // 处理未找到记录或未能更新的情况
                    System.out.println("未找到对应的购物车项或更新失败");
                }
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 拼接查詢條件
    private void addFiltering(StringBuilder sqlBuilder, CartQueryParams queryParams) {
        if (queryParams.getUserId() != null) {
            sqlBuilder.append(" AND c.user_id = ?");
        }
    }

    // 查詢購物車內商品總數
    public Integer countCartItems(CartQueryParams queryParams) {
        Integer total = 0;

        try {
            connect();
            String sql = "SELECT count(*) FROM cart WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, queryParams.getUserId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            pstmt.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL count錯誤");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    // 從購物車減少一件商品
    public void decrementToCart(Integer userId, Integer productId) {

        try {
            connect();
            conn.setAutoCommit(false); // 開始事務
            String sql = "UPDATE cart SET quantity = quantity - 1 WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                // 处理未找到记录或未能更新的情况
                System.out.println("未找到对应的购物车项或更新失败");
                conn.rollback(); // 回滾事務
                return;
            }

            // 如果購物車數量為0，則刪除購物車中的項目
            sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ? AND quantity = 0;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

            conn.commit(); // 提交事務
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }

    // 從購物車刪除一種商品
    public void deleteFromCart(Integer userId, Integer productId) {

        try {
            connect();

            String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }


        // 購物車商品數量input與資料庫連動
    public void quantitychange(Integer userId, Integer productId, Integer quantity) {

        try {
            connect();

            String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ? ";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,quantity);
            pstmt.setInt(2, userId);
            pstmt.setInt(3,productId);
            pstmt.executeUpdate();    

            pstmt.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }

}
