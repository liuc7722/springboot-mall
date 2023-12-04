package com.example.demo.dto;

import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 前端會傳來一個JsonObject，大概長這樣
 {
    "buyItemList":[
        {
         "productId" : 9,
         "quantity" : 1
        },
        {
         "productId" : 10,
         "quantity" : 2
        }
    ]
 } 
 為了滿足上述，我們需要準備一個List 叫做 buyItemList 
 裡面又要有很多個一種物件
 所以就要多寫個class BuyItem啦 ^ ^

 but，我們的又龜毛，還得附上運送地址shippingAddress
  {
    "shippingAddress" : "abc",
    "buyItemList":[
        {
         "productId" : 9,
         "quantity" : 1
        },
        {
         "productId" : 10,
         "quantity" : 2
        }
    ]
 } 
*/
@Data
public class CreateOrderRequest {
    
    @NotNull
    String shippingAddress; // 運送地址
    @NotEmpty
    @Valid
    private List<BuyItem> buyItemList;
}
