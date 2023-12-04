package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.util.OrderItemDetail;

import jakarta.validation.Valid;


@Component
public class OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao; // 想要取得商品的操作~

    @Autowired
    UserDao userDao;
    

    // 創建訂單
    @Transactional
    public Integer createOrder(Integer userId, @Valid CreateOrderRequest createOrderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);
        if(user == null){
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        // 計算訂單總花費
        int totalPrice = 0;
        List<OrderItem> orderItemList = new ArrayList<>(); // order_item表對應的是OrderItem

        for (var buyItem : createOrderRequest.getBuyItemList()) {
            // 先查詢此商品且取得單價
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if(product == null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if(product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 庫存不足，剩餘庫存 {} ，欲購買數量 {}", buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            
            // 更新商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            int amount =  product.getPrice() * buyItem.getQuantity(); // 計算每個商品的總價
            totalPrice += amount; 

            // 轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setPrice(amount); // 每個商品的總價
            
            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalPrice, createOrderRequest.getShippingAddress());

        // 創建訂單細項
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
    // 對於order資料表而言，不須知道購買什麼商品，只須知道哪個用戶和總價
    // 若想知道購買什麼商品，請去order_item查看
    // 所以需要一個createOrder方法處理order資料表、再一個createOrderItem方法處理order_item資料表
    // 而對於order_item而言，須要知道哪張訂單，及"每個"商品的id、數量、總價(orderItemList)
    // 結論，因為有兩個資料表，所以我們須先增建order表，再增建order_item表

    // 查詢訂單
    public Order getOrderById(Integer orderId) {

        // 先查詢訂單"總資訊"
        Order order = orderDao.getOrderById(orderId);

        // 再查詢訂單"細項"並set給order (此時Order已擴充)
        List<OrderItemDetail> orderItemsList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemsList);
        
        return order;
    }

    // 查詢訂單列表(數量+完整的訂單資訊)
    public List<Order> getOrders(OrderQueryParams queryParams) {
        List<Order> orderList = orderDao.getOrders(queryParams);

        // getOrders方法只能取得未擴充的Order資訊
        for(Order order : orderList){
            List<OrderItemDetail> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);   // pass by refference          
        }
        return orderList;
    }

    // 取得訂單總數
    public Integer countOrder(OrderQueryParams queryParams) {
        return orderDao.countDao(queryParams);
    }
}
