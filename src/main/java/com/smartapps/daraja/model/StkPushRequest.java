package com.smartapps.daraja.model;
public class StkPushRequest {
    private String businessShortCode;
    private String password;
    private String timestamp;
    private String transactionType;
    private String amount;
    private String partyA;
    private String partyB;
    private String phoneNumber;
    private String callBackURL;
    private String accountReference;
    private String transactionDesc;

    // Private constructor to enforce object creation via Builder
    private StkPushRequest(Builder builder) {
        this.businessShortCode = builder.businessShortCode;
        this.password = builder.password;
        this.timestamp = builder.timestamp;
        this.transactionType = builder.transactionType;
        this.amount = builder.amount;
        this.partyA = builder.partyA;
        this.partyB = builder.partyB;
        this.phoneNumber = builder.phoneNumber;
        this.callBackURL = builder.callBackURL;
        this.accountReference = builder.accountReference;
        this.transactionDesc = builder.transactionDesc;
    }

    // Getters
    public String getBusinessShortCode() { return businessShortCode; }
    public String getPassword() { return password; }
    public String getTimestamp() { return timestamp; }
    public String getTransactionType() { return transactionType; }
    public String getAmount() { return amount; }
    public String getPartyA() { return partyA; }
    public String getPartyB() { return partyB; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCallBackURL() { return callBackURL; }
    public String getAccountReference() { return accountReference; }
    public String getTransactionDesc() { return transactionDesc; }

    @Override
    public String toString() {
        return "LipaNaMpesaRequest{" +
                "businessShortCode='" + businessShortCode + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount='" + amount + '\'' +
                ", partyA='" + partyA + '\'' +
                ", partyB='" + partyB + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", callBackURL='" + callBackURL + '\'' +
                ", accountReference='" + accountReference + '\'' +
                ", transactionDesc='" + transactionDesc + '\'' +
                '}';
    }

    // Static Builder Class
    public static class Builder {
        private String businessShortCode;
        private String password;
        private String timestamp;
        private String transactionType;
        private String amount;
        private String partyA;
        private String partyB;
        private String phoneNumber;
        private String callBackURL;
        private String accountReference;
        private String transactionDesc;

        public Builder setBusinessShortCode(String businessShortCode) {
            this.businessShortCode = businessShortCode;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setTransactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPartyA(String partyA) {
            this.partyA = partyA;
            return this;
        }

        public Builder setPartyB(String partyB) {
            this.partyB = partyB;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setCallBackURL(String callBackURL) {
            this.callBackURL = callBackURL;
            return this;
        }

        public Builder setAccountReference(String accountReference) {
            this.accountReference = accountReference;
            return this;
        }

        public Builder setTransactionDesc(String transactionDesc) {
            this.transactionDesc = transactionDesc;
            return this;
        }

        public StkPushRequest build() {
            return new StkPushRequest(this);
        }
    }
}
