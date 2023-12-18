package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;
import com.example.demo.service.EcpayPaymentService;
import com.example.demo.service.OrderService;

@RestController
public class EcpayPaymentController {

    EcpayPaymentService ecpayPaymentService;

    @Autowired
    OrderService orderService;

    public EcpayPaymentController(EcpayPaymentService ecpayPaymentService) {
        this.ecpayPaymentService = ecpayPaymentService;
    }

    // 創建一筆綠界訂單和交易
    @PostMapping("/ecpay-checkout/{orderId}")
    public ResponseEntity<String> ecpayCheckout(@PathVariable Integer orderId){
        Order order = orderService.getOrderById(orderId);
        String paymentForm = null;
        if(order != null)
            paymentForm = ecpayPaymentService.createPaymentRequest(order);
        else
            System.out.println("order是null");
        return ResponseEntity.status(HttpStatus.OK).body(paymentForm);
    }
}
