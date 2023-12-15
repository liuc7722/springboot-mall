package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response.BaseResponse;
import com.example.demo.dto.TransactionIdRequest;
import com.example.demo.dto.VerifyPaymentRequest;
import com.example.demo.model.CheckoutPaymentRequestForm;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.EmailSendService;
import com.example.demo.service.LinePayService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LinePayController {

    @Autowired
    LinePayService linePayService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    EmailSendService sendService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;
    
    // 創建一筆Linepay訂單和交易
    @PostMapping("/line-pay/checkout")
    public ResponseEntity<String> linePayCheckout(@RequestBody CheckoutPaymentRequestForm form) {

        try {
            LinePayService linePayService = new LinePayService();
            String paymentResponse = linePayService.initiatePayment(form);

            // 解析Line pay取得web url
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(paymentResponse);
            String webUrl = responseJson.path("info").path("paymentUrl").path("web").asText();

            // 保存transactionId到訂單
            String transactionId = responseJson.path("info").path("transactionId").asText();
            if (transactionId != null && !transactionId.isEmpty()) {
                orderService.saveTransactionIdForOrder(form.getOrderId(), transactionId);
            }

            return ResponseEntity.ok(webUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment initiation failed.");
        }
    }

    // 驗證訂單
    @PostMapping("/line-pay/verify")
    public ResponseEntity<?> verifyPayment(HttpServletRequest r, @RequestBody TransactionIdRequest request) {
        String token = r.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = jwtUtil.getUserIdFromToken(token); // 從JWT提取用戶ID
        try {
            // 和line pay驗證需要金額和交易序號，由交易序號從資料庫取得金額
            Integer amount = orderService.getOrderAmountByTransactionId(request.getTransactionId());
            if (amount == null)
                return ResponseEntity.badRequest().body("Order amount not found for the given transactionId");

            // 再進行驗證交易
            boolean isConfirmed = linePayService.confirmPayment(request.getTransactionId(), amount);

            if (isConfirmed) {
                // 更新訂單狀態、購物車狀態、業務邏輯
                orderService.updateOrderStatusToPay(request.getOrderId());
                cartService.remove(userId); // 刪除此人在購物車的紀錄(假定用戶只能購買全部購物車內的商品)
                
                // 寄送確認付款email
                User user = userService.getUserById(userId);
                if(user != null)
                    sendEmail(user.getEmail(), "已確認付款", "您的訂單編號是" + request.getOrderId());


                return new ResponseEntity<>(new BaseResponse(0, "交易成功"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BaseResponse(1, "交易失敗"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
        }
    }

    // 發送郵件
    private void sendEmail(String toEmail, String subject, String body) {
        sendService.sendEmail(toEmail, subject, body);
    }

}
