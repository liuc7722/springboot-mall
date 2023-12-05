package com.example.demo.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.constant.OrderStatus;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.util.OrderItemDetail;

@Component
public class OrderDao extends BaseDao {

    // 創建訂單
    public Integer createOrder(Integer userId, int totalPrice, String shippingAddress) {
        Integer orderId = null;
        try {
            connect();
            String sql = "INSERT INTO `order` (user_id, total_price, order_date, shipping_address," + //
                    " status) VALUES (?, ?, NOW(), ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, totalPrice);
            pstmt.setString(3, shippingAddress);
            pstmt.setString(4, "PROCESSING");

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            rs.next();
            orderId = rs.getInt(1);

            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return orderId;
    }
    // 對於order資料庫而言，不須知道購買什麼商品，只須知道哪個用戶和總價
    // 若想知道購買什麼商品，請去order_item查看

    // 創建訂單細項
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        try {
            connect();
            conn.setAutoCommit(false); // 關閉自動提交

            String sql = "INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            for (var orderItem : orderItemList) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, orderItem.getProductId());
                pstmt.setInt(3, orderItem.getQuantity());
                pstmt.setInt(4, orderItem.getPrice());

                pstmt.addBatch(); // 將命令添加到批處理中
            }
            pstmt.executeBatch(); // 執行批次處理
            conn.commit(); // 提交事務

            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 查詢訂單
    public Order getOrderById(Integer orderId) {
        Order order = null;
        try {
            connect();
            String sql = "SELECT * FROM `order` WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setShippingAddress(rs.getString("shipping_address"));
                // 將String轉成enum
                String statusStr = rs.getString("status");
                OrderStatus status = OrderStatus.valueOf(statusStr);
                order.setStatus(status);
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    // 查詢訂單細項
    public List<OrderItemDetail> getOrderItemsByOrderId(Integer orderId) {
        List<OrderItemDetail> orderItemList = new ArrayList<>();
        try {
            connect();
            String sql = "SELECT " +
                    "oi.order_item_id, " +
                    "oi.order_id, " +
                    "oi.product_id, " +
                    "oi.quantity, " +
                    "oi.price, " +
                    "p.title, " +
                    "p.photo_url " +
                    "FROM order_item oi " +
                    "JOIN product p ON oi.product_id = p.product_id " +
                    "WHERE oi.order_id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItemDetail item = new OrderItemDetail();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getInt("price")); // 之後改成setDouble
                item.setTitle(rs.getString("title"));
                item.setPhotoUrl(rs.getString("photo_url"));
                orderItemList.add(item);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return orderItemList;
    }

    // 查詢訂單列表
    public List<Order> getOrders(OrderQueryParams queryParams) {
        List<Order> orderList = new ArrayList<>();
        try {
            connect();
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM `order` WHERE 1=1");
            // 查詢條件
            addFiltering(sqlBuilder, queryParams);
            // 排序
            sqlBuilder.append(" ORDER BY order_date DESC"); // 預設就是日期，不可改
            // 分頁
            sqlBuilder.append(" LIMIT ").append(queryParams.getLimit()).append(" OFFSET ")
                    .append(queryParams.getOffset());

            pstmt = conn.prepareStatement(sqlBuilder.toString());

            // 設置查詢參數
            pstmt.setInt(1, queryParams.getUserId());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setShippingAddress(rs.getString("shipping_address"));
                // 將String 轉成 enum
                String statusStr = rs.getString("status");
                OrderStatus status = OrderStatus.valueOf(statusStr);
                order.setStatus(status);
                orderList.add(order);
            }

            pstmt.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    // 取得訂單總數
    public Integer countDao(OrderQueryParams queryParams) {
        Integer total = 0;
        try {
            connect();
            StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) as c FROM `order` WHERE 1=1");
            addFiltering(sqlBuilder, queryParams);
            pstmt = conn.prepareStatement(sqlBuilder.toString());
            pstmt.setInt(1, queryParams.getUserId());
            rs = pstmt.executeQuery();
            if(rs.next())
                total = rs.getInt("c");

            pstmt.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    // 拼接查詢條件
    private void addFiltering(StringBuilder sqlBuilder, OrderQueryParams queryParams) {
        if (queryParams.getUserId() != null) {
            sqlBuilder.append(" AND user_id = ?");
        }
    }

}
