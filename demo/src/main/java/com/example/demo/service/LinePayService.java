package com.example.demo.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CheckoutPaymentRequestForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

@Service
public class LinePayService {

    public static String encrypt(final String keys, final String data) {
        return toBase64String(HmacUtils.getHmacSha256(keys.getBytes()).doFinal(data.getBytes()));
    }

    public static String toBase64String(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }

    private static final String LINE_PAY_URL = "https://sandbox-api-pay.line.me/v3/payments/request";
    private static final String CHANNEL_ID = "2002209238";
    private static final String CHANNEL_SECRET = "9768066aa944c300612f3a0c607f4a4c";
    private static final String LINE_PAY_CONFIRM_URL_TEMPLATE = "https://sandbox-api-pay.line.me/v3/payments/%s/confirm";

    // 創建交易
    public String initiatePayment(CheckoutPaymentRequestForm form) throws JsonProcessingException {
        // 創建 ObjectMapper 實例
        ObjectMapper mapper = new ObjectMapper();

        // 設置請求Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LINE-ChannelId", CHANNEL_ID);

        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();

        // signature 為 Request Header中的X-LINE-Authorization
        String signature = encrypt(CHANNEL_SECRET,
                CHANNEL_SECRET + requestUri + mapper.writeValueAsString(form) + nonce);

        headers.set("X-LINE-Authorization", signature);
        headers.set("X-LINE-Authorization-Nonce", nonce);

        // 創建請求實體
        HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(form), headers);

        // 創建 RestTemplate 實例
        RestTemplate restTemplate = new RestTemplate();

        // 發送 POST 請求
        ResponseEntity<String> response = restTemplate.postForEntity(LINE_PAY_URL, requestEntity, String.class);
        String responseBody = response.getBody();

        // String returnCode = responseJson.path("returnCode").asText();
        // String returnMessage = responseJson.path("returnMessage").asText();
        // System.out.println(returnCode);
        // System.out.println(returnMessage);
        // 返回支付網址
        return responseBody;
    }

    // 認證交易
    public boolean confirmPayment(String transactionId, Integer amount) throws JsonProcessingException {
        // 確認支付的請求URL
        String confirmUrl = String.format(LINE_PAY_CONFIRM_URL_TEMPLATE, transactionId);

        // 確認支付的請求body
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("amount", amount);
        requestBody.put("currency", "TWD");

        // 請求的Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LINE-ChannelId", CHANNEL_ID);
        String nonce = UUID.randomUUID().toString();
        String signature = encrypt(CHANNEL_SECRET,
                CHANNEL_SECRET + confirmUrl + requestBody.toString() + nonce);
        headers.set("X-LINE-Authorization", signature);
        headers.set("X-LINE-Authorization-Nonce", nonce);
        System.out.println("signature: " + signature);
        System.out.println("nonce: " + nonce);
        System.out.println("confirmUrl: " + confirmUrl);

        // 創建請求實體
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // 發送請求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(confirmUrl, requestEntity, String.class);

        // 檢查response
        JsonNode responseJson = mapper.readTree(response.getBody());
        String returnCode = responseJson.path("returnCode").asText();
        String returnMessage = responseJson.path("returnMessage").asText();

        System.out.println("returnCode: " + returnCode);
        System.out.println("returnMessage: " + returnMessage);
        return "0000".equals(responseJson.path("returnCode").asText());
    }
}
