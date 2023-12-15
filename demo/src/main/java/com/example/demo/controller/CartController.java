package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.html.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CartQueryParams;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import com.example.demo.util.CartItemDetail;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Page;
import com.example.demo.util.SessionUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Validated // 加上它@MAX,@MIN才會生效
// @CrossOrigin(origins = "*") // 允許不同網域的網頁呼叫API
@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    JwtUtil jwtUtil;

    // 查詢購物車列表
    @GetMapping("/users/carts")
    @Tag(name = "購物車API")
    @Operation(summary = "查詢購物車列表", description = "可加上查詢條件")
    public ResponseEntity<Page<CartItemDetail>> getCartItems(
            HttpServletRequest request,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // 驗證已經給攔截器做了
        // if(token == null || !jwtUtil.validationToken(token)){
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        // }
        Integer userId = jwtUtil.getUserIdFromToken(token); // 從JWT提取用戶ID
        CartQueryParams queryParams = new CartQueryParams();
        queryParams.setUserId(userId);
        queryParams.setLimit(limit);
        queryParams.setOffset(offset);

        Page<CartItemDetail> page = new Page<>();
        List<CartItemDetail> cartItems = cartService.getCartItems(queryParams);
        Integer total = cartService.countCartItems(queryParams);
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(cartItems);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    // 添加一件商品到購物車
    @PutMapping("/users/carts/{productId}/increment")
    @Tag(name = "購物車API")
    @Operation(summary = "添加一件商品到購物車")
    public ResponseEntity<?> incrementToCart(HttpServletRequest request, @PathVariable Integer productId) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token); // 從JWT中取得userid
        cartService.incrementToCart(userId, productId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 從購物車減少一件商品
    @PutMapping("/users/carts/{productId}/decrement")
    @Tag(name = "購物車API")
    @Operation(summary = "從購物車減少一件商品")
    public ResponseEntity<?> decrementToCart(HttpServletRequest request, @PathVariable Integer productId) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);
        cartService.decrementToCart(userId, productId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 從購物車刪除一種商品
    @DeleteMapping("/users/carts/{productId}")
    @Tag(name = "購物車API")
    @Operation(summary = "從購物車刪除一種商品")
    public ResponseEntity<?> deleteFromCart(HttpServletRequest request, @PathVariable Integer productId) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);
        cartService.deleteFromCart(userId, productId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 購物車商品數量input與資料庫連動
    @PutMapping("/users/carts/{productId}/{quantity}")
    @Tag(name = "購物車API")
    @Operation(summary = "購物車商品數量input與資料庫連動 ")
    public ResponseEntity quantitychange(HttpServletRequest request, @PathVariable Integer productId,
            @PathVariable Integer quantity) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token);
        cartService.quantitychange(userId, productId, quantity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
/*
 * 當用戶選擇一個商品並將其添加到購物車時，系統會創建一個新的 CartItem 紀錄，並將選擇的商品的 ProductID 設定在這個記錄中。
 * 顯示購物車：
 * 當用戶查看購物車時，系統會查詢購物車明細表，根據每個 CartItem 的 ProductID，從商品表中獲取相關商品的詳細信息，然後顯示給用戶。
 * 購物車修改：
 * 如果用戶決定更改購物車中某個商品的數量或刪除某個商品，系統將根據 ProductID 來識別要修改的具體商品。
 * 
 */
