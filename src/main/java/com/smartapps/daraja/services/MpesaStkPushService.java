package com.smartapps.daraja.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartapps.daraja.model.StkPushRequest;
import com.smartapps.daraja.model.StkPushResponse;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Service
public class MpesaStkPushService {

    @Value("${mpesa.shortcode}")
    private String businessShortCode;

    @Value("${mpesa.passkey}")
    private String passkey;

    @Value("${mpesa.stkpush_url}")
    private String stkPushUrl;

    @Value("${mpesa.callback_url}")
    private String callbackUrl;
    @Autowired
    private MpesaAuthService mpesaAuthService;
    private final OkHttpClient client = new OkHttpClient();
//    public StkPushResponse initiateStkPushdd(String phoneNumber, double amount) {
//        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String password = generatePassword(businessShortCode, passkey, timestamp);
//        StkPushRequest request = new StkPushRequest.Builder()
//                .setBusinessShortCode("174379")
//                .setPassword(password)
//                .setTimestamp(timestamp)
//                .setTransactionType("CustomerPayBillOnline")
//                .setAmount(String.valueOf((int) amount))
//                .setPartyA(phoneNumber)
//                .setPartyB("174379")
//                .setPhoneNumber(phoneNumber)
//                .setCallBackURL(callbackUrl)
//                .setAccountReference("INV-001")
//                .setTransactionDesc("Payment for Order #001")
//                .build();
//        System.out.println(request.toString());
//
//        String accessToken = mpesaAuthService.getAccessToken();
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<StkPushRequest> entity = new HttpEntity<>(request, headers);
//        ResponseEntity<StkPushResponse> response = restTemplate.exchange(stkPushUrl, HttpMethod.POST, entity, StkPushResponse.class);
//
//        return response.getBody();
//    }

    //    public StkPushResponse initiateStkPush(String phoneNumber, double amount) throws IOException {
//        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String password = generatePassword(businessShortCode, passkey, timestamp);
//        StkPushRequest request = new StkPushRequest.Builder()
//                .setBusinessShortCode("174379")
//                .setPassword(password)
//                .setTimestamp(timestamp)
//                .setTransactionType("CustomerPayBillOnline")
//                .setAmount(String.valueOf((int) amount))
//                .setPartyA(phoneNumber)
//                .setPartyB("174379")
//                .setPhoneNumber(phoneNumber)
//                .setCallBackURL(callbackUrl)
//                .setAccountReference("INV-001")
//                .setTransactionDesc("Payment for Order #001")
//                .build();
//        System.out.println(request.toString());
//        String accessToken = mpesaAuthService.getAccessToken();
//        String jsonBody = requestBody(request);
//        System.out.println("Request Body: " + jsonBody);
//
//        Request okHttpRequest = new Request.Builder()
//                .url(stkPushUrl)
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .addHeader("Content-Type", "application/json")
//                .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
//                .build();
//
//        try (Response response = client.newCall(okHttpRequest).execute()) {
//            if (!response.isSuccessful()) {
//                throw new Exception("Unexpected code " + response);
//            } else System.out.println("Full Response: " + response.body().string());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
    public StkPushResponse initiateStkPush(String phoneNumber, double amount) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String password = generatePassword(businessShortCode, passkey, timestamp);

        StkPushRequest request = new StkPushRequest.Builder()
                .setBusinessShortCode("174379")
                .setPassword(password)
                .setTimestamp(timestamp)
                .setTransactionType("CustomerPayBillOnline")
                .setAmount(String.valueOf((int) amount))
                .setPartyA(phoneNumber)
                .setPartyB("174379")
                .setPhoneNumber(phoneNumber)
                .setCallBackURL(callbackUrl)
                .setAccountReference("INV-001")
                .setTransactionDesc("Payment for Order #001")
                .build();

        String accessToken = mpesaAuthService.getAccessToken();
        String jsonBody = requestBody(request);

        System.out.println("Request Body: {}" + jsonBody);

        Request okHttpRequest = new Request.Builder()
                .url(stkPushUrl)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(okHttpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code() + " - " + response.message());
            }

            try (ResponseBody responseBody = response.body()) {
                if (responseBody != null) {
                    String responseString = responseBody.string();
                    return convertFromJson(responseString, StkPushResponse.class);
                } else {
                    throw new IOException("Empty response body");
                }
            }
        }
    }

    private <T> T convertFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
    private String generatePassword(String shortcode, String passkey, String timestamp) {
        String data = shortcode + passkey + timestamp;
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }
    public String requestBody(StkPushRequest request) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", request.getBusinessShortCode());
        jsonObject.put("Password", request.getPassword());
        jsonObject.put("Timestamp", request.getTimestamp());
        jsonObject.put("TransactionType", request.getTransactionType());
        jsonObject.put("Amount", request.getAmount());
        jsonObject.put("PhoneNumber", request.getPhoneNumber());
        jsonObject.put("PartyA", request.getPartyA());
        jsonObject.put("PartyB", request.getPartyB());
        jsonObject.put("CallBackURL", request.getCallBackURL());
        jsonObject.put("AccountReference", request.getAccountReference());
        jsonObject.put("TransactionDesc", request.getTransactionDesc());
        jsonArray.put(jsonObject);
        return jsonArray.toString().replaceAll("[\\[\\]]", "");
    }

}
