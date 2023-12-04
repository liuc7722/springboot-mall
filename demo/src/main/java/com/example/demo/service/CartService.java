package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;

@Component
public class CartService {
    private final static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    CartDao cartDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    // 添加商品到購物車
    public void addToCart(Integer userId, Integer productId) {
        // // 檢查用戶是否存在
        // User user = userDao.getUserById(userId);
        // if (user == null) {
        //     log.warn("該 userId {} 不存在", userId);
        //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // }
        // 檢查商品和庫存
        Product product = productDao.getProductById(productId);
        // 檢查 product 是否存在、庫存是否足夠
        if (product == null) {
            log.warn("商品 {} 不存在", product.getProductId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (product.getStock() < 1) {
            log.warn("商品 {} 已無庫存!", product.getProductId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 先使用getCartByUserId取得Cart，若為null則使用userId新增一個購物車
        // 再藉由Cart.getCartId()來添加商品到購物車細項表
        Cart cart = cartDao.getCartByUserId(userId);
        Integer cartId = null;
        if (cart == null) {
            // 創建一筆資料到cart表並取得Cart
            cartId = cartDao.createCart(userId);
        } else {
            cartId = cart.getCartId();
        }

        // 創建一個購物車細項
        cartDao.createCartItem(cartId, productId);
    }
    // 注意，因為有兩個資料表，其中cart中每一個user_id會對應到一個cart_id! 
    // 所以先判斷此用戶有無購物車的紀錄，若無，則新增一筆購物車紀錄，再回傳購物車的id
    // 若有，直接取得購物車的id
    // 最後，確認有後就不關cart表的事了
}
