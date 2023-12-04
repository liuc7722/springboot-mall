package com.example.demo.model;

import lombok.Data;

// 在查詢訂單細項時，每一個訂單都有很多筆商品資訊，我們稱呼取OrderItem
// 也就是每一個row，都是一個物件
// 全部合起來就是一個List
// 所以查詢訂單時，回傳order表(訂單總資訊)比較簡單，但回傳order_item表就要回傳List<OrderItem>
// 根據OrderId，在order_item table裡面，查詢那筆訂單中的所有品項
@Data
public class OrderItem {
    Integer orderItemId;
    Integer orderId;
    Integer productId;
    Integer quantity;
    Integer price;   // 商品單項的總價
}
