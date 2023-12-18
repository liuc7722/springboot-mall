package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    JwtUtil jwtUtil;

    // 創建訂單
    @PostMapping("/users/orders") 
    @Tag(name = "訂單API")
    @Operation(summary = "創建訂單", description = "網址請輸入users/使用者ID/orders，如users/9/orders，表示使用者ID為9的使用者新增一筆訂單，訂單資訊放在RequestBody內")
    public ResponseEntity<?> createOrder(HttpServletRequest request,
            @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // 查詢訂單列表(含數量+完整的訂單資訊)
    @GetMapping("/users/orders")
    @Tag(name = "訂單API")
    @Operation(summary = "查詢訂單列表", description = "網址請輸入users/使用者ID/orders，如users/9/orders")
    public ResponseEntity<Page<Order>> getOrders(
            HttpServletRequest request,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ) {
                
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);

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
