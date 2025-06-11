package com.smartapps.daraja.controller;

import com.smartapps.daraja.Repository.MpesaTransactionRepository;
import com.smartapps.daraja.model.MpesaTransaction;
import com.smartapps.daraja.model.StkPushResponse;
import com.smartapps.daraja.services.MpesaAuthService;
import com.smartapps.daraja.services.MpesaStkPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apis")
public class MpesaController {
    @Autowired
    private MpesaStkPushService mpesaStkPushService;

    @Autowired
    private MpesaAuthService mpesaAuthService;
    @Autowired
    private MpesaTransactionRepository repository;

    @GetMapping("/authenticate")
    public Map<String, String> authenticate() {
        String token = mpesaAuthService.getAccessToken();
        return Map.of("accessToken", token);
    }

    @PostMapping("/confirmationUrl")
    public MpesaTransaction confirmationUrl(@RequestBody MpesaTransaction transaction) {
        return repository.save(transaction);
    }
@GetMapping("/keepalive")
public String keepAlive(){
        return "requested";
}
    @PostMapping("/validationUrl")
    public ResponseEntity<Map<String, String>> validationUrl() {
        Map<String, String> response = new HashMap<>();
        response.put("ResultCode", "0");
        response.put("ResultDesc", "Accepted");

        // Return the response with HTTP 200 status
        return ResponseEntity.ok(response);
    }

    @PostMapping("/calbackurl")
    public void stkPushCallback(@RequestBody String callbackData) {
        System.out.println("Received STK Push Callback: " + callbackData);
    }

    @PostMapping("/stkpush")
    public String stkPush(@RequestBody Map<String, String> requestData) throws IOException {
        String phoneNumber = requestData.get("phoneNumber").replaceFirst("^0", "254");
        double amount = Double.parseDouble(requestData.get("amount"));
        return mpesaStkPushService.initiateStkPush(phoneNumber, amount);
    }
}
