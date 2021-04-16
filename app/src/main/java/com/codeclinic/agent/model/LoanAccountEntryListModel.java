package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanAccountEntryListModel {
    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("transactionType")
    @Expose
    private String transactionType;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;
    @SerializedName("entryType")
    @Expose
    private String entryType;
    @SerializedName("chargeType")
    @Expose
    private String chargeType;
    @SerializedName("loanNumber")
    @Expose
    private String loanNumber;
    @SerializedName("loanServiceRef")
    @Expose
    private String loanServiceRef;
    @SerializedName("accountingServiceRef")
    @Expose
    private Object accountingServiceRef;
    @SerializedName("narrative")
    @Expose
    private Object narrative;

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanServiceRef() {
        return loanServiceRef;
    }

    public void setLoanServiceRef(String loanServiceRef) {
        this.loanServiceRef = loanServiceRef;
    }

    public Object getAccountingServiceRef() {
        return accountingServiceRef;
    }

    public void setAccountingServiceRef(Object accountingServiceRef) {
        this.accountingServiceRef = accountingServiceRef;
    }

    public Object getNarrative() {
        return narrative;
    }

    public void setNarrative(Object narrative) {
        this.narrative = narrative;
    }
}
