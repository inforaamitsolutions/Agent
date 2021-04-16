package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallmentListModel {
    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("loanNumber")
    @Expose
    private String loanNumber;
    @SerializedName("expectedPrincipal")
    @Expose
    private Integer expectedPrincipal;
    @SerializedName("paidPrinciple")
    @Expose
    private Integer paidPrinciple;
    @SerializedName("expectedInterest")
    @Expose
    private Integer expectedInterest;
    @SerializedName("paidInterest")
    @Expose
    private Integer paidInterest;
    @SerializedName("expectedCharges")
    @Expose
    private Integer expectedCharges;
    @SerializedName("paidCharges")
    @Expose
    private Integer paidCharges;
    @SerializedName("expectedPenalties")
    @Expose
    private Integer expectedPenalties;
    @SerializedName("paidPenalties")
    @Expose
    private Integer paidPenalties;
    @SerializedName("scheduledPayment")
    @Expose
    private Integer scheduledPayment;
    @SerializedName("expectedTotal")
    @Expose
    private Integer expectedTotal;
    @SerializedName("paidTotal")
    @Expose
    private Integer paidTotal;
    @SerializedName("commenceDate")
    @Expose
    private String commenceDate;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("installmentNo")
    @Expose
    private Integer installmentNo;
    @SerializedName("installmentDays")
    @Expose
    private Integer installmentDays;

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

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Integer getExpectedPrincipal() {
        return expectedPrincipal;
    }

    public void setExpectedPrincipal(Integer expectedPrincipal) {
        this.expectedPrincipal = expectedPrincipal;
    }

    public Integer getPaidPrinciple() {
        return paidPrinciple;
    }

    public void setPaidPrinciple(Integer paidPrinciple) {
        this.paidPrinciple = paidPrinciple;
    }

    public Integer getExpectedInterest() {
        return expectedInterest;
    }

    public void setExpectedInterest(Integer expectedInterest) {
        this.expectedInterest = expectedInterest;
    }

    public Integer getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Integer paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Integer getExpectedCharges() {
        return expectedCharges;
    }

    public void setExpectedCharges(Integer expectedCharges) {
        this.expectedCharges = expectedCharges;
    }

    public Integer getPaidCharges() {
        return paidCharges;
    }

    public void setPaidCharges(Integer paidCharges) {
        this.paidCharges = paidCharges;
    }

    public Integer getExpectedPenalties() {
        return expectedPenalties;
    }

    public void setExpectedPenalties(Integer expectedPenalties) {
        this.expectedPenalties = expectedPenalties;
    }

    public Integer getPaidPenalties() {
        return paidPenalties;
    }

    public void setPaidPenalties(Integer paidPenalties) {
        this.paidPenalties = paidPenalties;
    }

    public Integer getScheduledPayment() {
        return scheduledPayment;
    }

    public void setScheduledPayment(Integer scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public Integer getExpectedTotal() {
        return expectedTotal;
    }

    public void setExpectedTotal(Integer expectedTotal) {
        this.expectedTotal = expectedTotal;
    }

    public Integer getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(Integer paidTotal) {
        this.paidTotal = paidTotal;
    }

    public String getCommenceDate() {
        return commenceDate;
    }

    public void setCommenceDate(String commenceDate) {
        this.commenceDate = commenceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Integer installmentNo) {
        this.installmentNo = installmentNo;
    }

    public Integer getInstallmentDays() {
        return installmentDays;
    }

    public void setInstallmentDays(Integer installmentDays) {
        this.installmentDays = installmentDays;
    }
}
