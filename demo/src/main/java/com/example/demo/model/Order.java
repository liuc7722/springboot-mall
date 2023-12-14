package com.example.demo.model;

import java.sql.Date;
import java.util.List;

import com.example.demo.constant.OrderStatus;
import com.example.demo.util.OrderItemDetail;

import lombok.Data;

@Data
public class Order {
    Integer orderId;
    Integer userId;
    Integer totalPrice;
    Date orderDate;
    String shippingAddress;
    OrderStatus status; 

    List<OrderItemDetail> orderItemList; // 擴充Order，回傳給前端的資訊要更多(雖然這樣就和table沒對應)\

    String transactionId; // 交易序號 
}
