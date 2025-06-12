package com.smartapps.daraja.services;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Base64;

@Service
public class MpesaAuthService {

    @Value("${mpesa.consumerKey}")
    private String consumerKey;
    @Value("${mpesa.consumerSecret}")
    private String consumerSecret;
    @Value("${mpesa.authUrl}")
    private String authUrl;
    public String getAccessToken() {
        String credentials = consumerKey + ":" + consumerSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.GET, request, String.class);
        System.out.println(extractToken(response.getBody()));
        return extractToken(response.getBody());
    }
    private String extractToken(String response){
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("access_token");
    }
}
