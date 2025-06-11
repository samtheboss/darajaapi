package com.smartapps.daraja.model;import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "mtransactions")
public class MpesaTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("TransactionType")
    @Column(name = "transaction_type")
    private String transactionType;

    @JsonProperty("TransID")
    @Column(name = "trans_id", unique = true, nullable = false)
    private String transID;
    @JsonProperty("TransTime")
    @Column(name = "trans_time")
    private String transTime;

    @JsonProperty("TransAmount")
    @Column(name = "trans_amount")
    private String transAmount;

    @JsonProperty("BusinessShortCode")
    @Column(name = "business_short_code")
    private String businessShortCode;

    @JsonProperty("BillRefNumber")
    @Column(name = "bill_ref_number")
    private String billRefNumber;

    @JsonProperty("InvoiceNumber")
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @JsonProperty("OrgAccountBalance")
    @Column(name = "org_account_balance")
    private String orgAccountBalance;

    @JsonProperty("ThirdPartyTransID")
    @Column(name = "third_party_trans_id")
    private String thirdPartyTransID;

    @JsonProperty("MSISDN")
    @Column(name = "msisdn")
    private String msisdn;

    @JsonProperty("FirstName")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("MiddleName")
    @Column(name = "middle_name")
    private String middleName;

    @JsonProperty("LastName")
    @Column(name = "last_name")
    private String lastName;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getBusinessShortCode() {
        return businessShortCode;
    }

    public void setBusinessShortCode(String businessShortCode) {
        this.businessShortCode = businessShortCode;
    }

    public String getBillRefNumber() {
        return billRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        this.billRefNumber = billRefNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrgAccountBalance() {
        return orgAccountBalance;
    }

    public void setOrgAccountBalance(String orgAccountBalance) {
        this.orgAccountBalance = orgAccountBalance;
    }

    public String getThirdPartyTransID() {
        return thirdPartyTransID;
    }

    public void setThirdPartyTransID(String thirdPartyTransID) {
        this.thirdPartyTransID = thirdPartyTransID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
