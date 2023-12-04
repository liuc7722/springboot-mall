package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.util.Page;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
public class OrderController {
    
    @Autowired
    OrderService orderService;

    // 創建訂單
    @PostMapping("/users/{userId}/orders") // 為何這樣設計!?
    public ResponseEntity<?> createOrder(@PathVariable Integer userId, // 原來路徑中有一個{},@PathVariable就會有一個參數!
                                      @RequestBody @Valid CreateOrderRequest createOrderRequest ){ 
        
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);
        

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    

    // 查詢訂單列表(含數量+完整的訂單資訊)
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
        @PathVariable Integer userId,
        @RequestParam(defaultValue = "10")@Max(1000) @Min(0) Integer limit,
        @RequestParam(defaultValue = "0")@Min(0) Integer offset,
        HttpSession session
    ){
        // 判斷有無登入過
        Integer sessionUserId = (Integer)session.getAttribute("orders");
        if(sessionUserId == null || !sessionUserId.equals(userId)){ // 驗證是否登入且訪問的是自己的訂單
            System.out.println("NOT FOUND");
            System.out.println(sessionUserId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        OrderQueryParams queryParams = new OrderQueryParams();
        queryParams.setUserId(userId);
        queryParams.setLimit(limit);
        queryParams.setOffset(offset);
        // 取得 order list
        List<Order> orderList = orderService.getOrders(queryParams);
        // 取得 order 總數
        Integer total = orderService.countOrder(queryParams);

        // 分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
