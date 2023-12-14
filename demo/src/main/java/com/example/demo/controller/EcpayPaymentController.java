package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EcpayPaymentService;

@RestController
@RequestMapping("/api/payment")
public class EcpayPaymentController {
    
    // EcpayPaymentService ecpayPaymentService;

    // @Autowired
    // public EcpayPaymentController(EcpayPaymentService ecpayPaymentService) {
    //     this.ecpayPaymentService = ecpayPaymentService;
    // }

    // @GetMapping("/ecpay-checkout/{orderId}")
    // public ResponseEntity<String> ecpayCheckout(@PathVariable Integer orderId){
    //     String paymentForm = ecpayPaymentService.createPaymentRequest();
    //     return ResponseEntity.status(HttpStatus.OK).body(paymentForm);
    // }
}
