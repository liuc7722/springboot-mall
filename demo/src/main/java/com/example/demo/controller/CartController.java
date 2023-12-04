package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import com.example.demo.util.Page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    // 添加商品到購物車
    @PostMapping("/users/{userId}/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Integer userId, Integer productId){
        cartService.addToCart(userId, productId);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 查看購物車列表
    // @GetMapping("/users/{userId}/cart")
    // public ResponseEntity<Page<Cart>> getCart(
    //     @PathVariable Integer userId,
    //     @RequestParam(defaultValue = "5")@Max(1000) @Min(0) Integer limit,
    //     @RequestParam(defaultValue = "0")@Min(0) Integer offset
    // ){

    //     return null;
    // }

    // 修改購物車

    // 刪除購物車
}
/*
 * 當用戶選擇一個商品並將其添加到購物車時，系統會創建一個新的 CartItem 紀錄，並將選擇的商品的 ProductID 設定在這個記錄中。
 * 顯示購物車：
 * 當用戶查看購物車時，系統會查詢購物車明細表，根據每個 CartItem 的 ProductID，從商品表中獲取相關商品的詳細信息，然後顯示給用戶。
 * 購物車修改：
 * 如果用戶決定更改購物車中某個商品的數量或刪除某個商品，系統將根據 ProductID 來識別要修改的具體商品。
 * 
 */
