package com.smartapps.daraja.services;

import com.smartapps.daraja.model.StkPushRequest;
import okhttp3.*;
import org.json.JSONObject;
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

    private final OkHttpClient client = new OkHttpClient();
    private final MpesaAuthService mpesaAuthService;

    public MpesaStkPushService(MpesaAuthService mpesaAuthService) {
        this.mpesaAuthService = mpesaAuthService;
    }

    public String initiateStkPush(String phoneNumber, double amount) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String password = Base64.getEncoder().encodeToString(
                (businessShortCode + passkey + timestamp).getBytes(StandardCharsets.UTF_8)
        );
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

        String jsonBody = createRequestBody(request);
        String accessToken = mpesaAuthService.getAccessToken();
        Request okHttpRequest = new Request.Builder()
                .url(stkPushUrl)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(okHttpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed: " + response.code() + " - " + response.message());
            }
            if (response.body() == null) throw new IOException("Empty response body");
            return response.body().string();
        }
    }

    private String createRequestBody(StkPushRequest request) {
        JSONObject json = new JSONObject();
        json.put("BusinessShortCode", request.getBusinessShortCode());
        json.put("Password", request.getPassword());
        json.put("Timestamp", request.getTimestamp());
        json.put("TransactionType", request.getTransactionType());
        json.put("Amount", request.getAmount());
        json.put("PartyA", request.getPartyA());
        json.put("PartyB", request.getPartyB());
        json.put("PhoneNumber", request.getPhoneNumber());
        json.put("CallBackURL", request.getCallBackURL());
        json.put("AccountReference", request.getAccountReference());
        json.put("TransactionDesc", request.getTransactionDesc());
        return json.toString();
    }
}
