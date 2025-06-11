package com.smartapps.daraja.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartapps.daraja.model.StkPushRequest;
import com.smartapps.daraja.model.StkPushResponse;
import okhttp3.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
//import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smartapps.daraja.utils.Converter.convertJsonToMap;

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

    public String initiateStkPush(String phoneNumber, double amount)
            throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String password = generatePassword(businessShortCode, passkey, timestamp);
        StkPushRequest request = new StkPushRequest.Builder()
                .setBusinessShortCode(businessShortCode)
                .setPassword(password)
                .setTimestamp(timestamp)
                .setTransactionType("CustomerPayBillOnline")
                .setAmount((int) amount)
                .setPartyA(phoneNumber)
                .setPartyB("174379")
                .setPhoneNumber(phoneNumber)
                .setCallBackURL(callbackUrl)
                .setAccountReference("INV-001")
                .setTransactionDesc("Payment for Order #001")
                .build();
        String accessToken = mpesaAuthService.getAccessToken();
        String jsonBody = requestBody(request);
        System.out.println("Request Body: " + jsonBody);
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
                    System.out.println("Response Body: " + responseString);
                    convertJsonToMap(responseString);
                    return responseString;
                } else {
                    throw new IOException("Empty response body");
                }
            }
        }
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
