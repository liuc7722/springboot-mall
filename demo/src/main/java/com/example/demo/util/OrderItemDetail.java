package com.example.demo.util;

import lombok.Data;

// 使用者查詢訂單時，除了原本的OrderItem(對應到資料表)的欄位，還需要商品名和圖片
// 所以再創建一個新的類(這邊不選擇直接在OrderItem擴充title和photoUrl)
@Data
public class OrderItemDetail {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
    private String title;
    private String photoUrl;
}
