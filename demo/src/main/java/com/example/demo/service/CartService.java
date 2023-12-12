package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.CartQueryParams;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.util.CartItemDetail;

@Component
public class CartService {
    private final static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    CartDao cartDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    // 查看購物車列表
    public List<CartItemDetail> getCartItems(CartQueryParams queryParams) {
        
        List<CartItemDetail> cartItems = cartDao.getCartItems(queryParams);
        return cartItems;
    }

    // 添加一件商品到購物車
    public void incrementToCart(Integer userId, Integer productId) {
        // 檢查用戶和商品
        User user = userDao.getUserById(userId);
        if (user == null) {
            System.out.println("無 " + userId + " 此用戶!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Product product = productDao.getProductById(productId);
        if (product == null) {
            System.out.println("無 " + productId + " 此商品!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 檢查購物車是否已存在此商品
        boolean exists = cartDao.CheckIfItemExists(userId, productId);
        // 添加或修改一件商品到購物車
        cartDao.addOrUpdateCart(userId, productId, exists);
    }

    // 查詢購物車內商品總數
    public Integer countCartItems(CartQueryParams queryParams) {
        return cartDao.countCartItems(queryParams);
    }

    // 從購物車減少一件商品
    public void decrementToCart(Integer userId, Integer productId) {
        // 檢查商品和用戶

        // 檢查購物車是否已存在此商品

        // 從購物車減少一件此商品
        cartDao.decrementToCart(userId, productId);
    }

    // 從購物車刪除一種商品
    public void deleteFromCart(Integer userId, Integer productId) {
        // 檢查商品和用戶

        // 檢查購物車是否已存在此商品

        // 從購物車刪除此用戶購買的此商品
        cartDao.deleteFromCart(userId, productId);
    }

        // 購物車商品數量input與資料庫連動
    public void quantitychange(Integer cartId, Integer productId) {

        cartDao.quantitychange(cartId, productId);
    }

}
