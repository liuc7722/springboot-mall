package com.example.demo.model;



import java.util.List;

import lombok.Data;


@Data
public class CheckoutPaymentRequestForm {

    Integer amount; // 訂單總價

    String currency; // ?

    String orderId; // 訂單id

    List<ProductPackageForm> packages;

    RedirectUrls redirectUrls; // 頁面

}