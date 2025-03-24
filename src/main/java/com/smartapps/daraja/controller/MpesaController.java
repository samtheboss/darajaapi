package com.smartapps.daraja.controller;

import com.smartapps.daraja.model.StkPushResponse;
import com.smartapps.daraja.services.MpesaAuthService;
import com.smartapps.daraja.services.MpesaStkPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/apis")
public class MpesaController {
    @Autowired
    private MpesaStkPushService mpesaStkPushService;

    @Autowired
    private MpesaAuthService mpesaAuthService;

    @GetMapping("/authenticate")
    public Map<String, String> authenticate() {
        String token = mpesaAuthService.getAccessToken();
        return Map.of("accessToken", token);
    }
    @GetMapping("/calbackurl")
    public String calbackurl() {
        return "callbackurl";
    }

    @PostMapping("/stkpush")
    public StkPushResponse stkPush(@RequestBody Map<String, String> requestData) throws IOException {
        String phoneNumber = requestData.get("phoneNumber");
        double amount = Double.parseDouble(requestData.get("amount"));
        return mpesaStkPushService.initiateStkPush(phoneNumber, amount);

    }
}
