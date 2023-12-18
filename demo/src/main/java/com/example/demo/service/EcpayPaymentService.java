package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.Order;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class EcpayPaymentService {

    private AllInOne all;

    public String createPaymentRequest(Order order) {
        // 根據訂單ID生成支付請求
        // 如: 查詢訂單詳情，獲取支付金額等訊息

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        all = new AllInOne("");
        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        /* 
        Date date = order.getOrderDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        System.out.println(formattedDate);
        */
        // 將Date轉格式，綠界要的是yyyy-MM-dd HH:mm:ss的格式
        Date date = order.getOrderDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        // System.out.println(formattedDate + "11111");
        obj.setMerchantTradeDate(formattedDate);
        obj.setTotalAmount(Integer.toString(order.getTotalPrice()));
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        // 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
        obj.setReturnURL("http://localhost:8080");
        obj.setNeedExtraPaidInfo("N");
        // 商店轉跳網址 (Optional)
        obj.setClientBackURL("http://192.168.1.37:8080/");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
}
