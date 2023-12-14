package com.example.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.Order;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import jakarta.annotation.PostConstruct;

@Service
public class EcpayPaymentService {

    private AllInOne all;


    

    public String createPaymentRequest(Order order){
        // 根據訂單ID生成支付請求
        // 如: 查詢訂單詳情，獲取支付金額等訊息

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        AioCheckOutOneTime obj = new AioCheckOutOneTime();
        // 設置參數
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2017/01/01 08:05:23");
        obj.setTotalAmount("1");
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        // 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
        obj.setReturnURL("http://localhost:8080");
        obj.setNeedExtraPaidInfo("N");
        // 商店轉跳網址 (Optional)
        obj.setClientBackURL("http://192.168.1.37:8080/");
        return this.all.aioCheckOut(obj, null);
    }
}
