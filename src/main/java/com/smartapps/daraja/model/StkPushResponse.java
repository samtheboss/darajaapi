package com.smartapps.daraja.model;

import lombok.Data;

@Data
public class StkPushResponse {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResponseCode;
    private String ResponseDescription;
    private String CustomerMessage;
}
