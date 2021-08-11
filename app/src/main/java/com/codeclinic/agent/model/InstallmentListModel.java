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
    private double expectedInterest;
    @SerializedName("paidInterest")
    @Expose
    private double paidInterest;
    @SerializedName("expectedCharges")
    @Expose
    private double expectedCharges;
    @SerializedName("paidCharges")
    @Expose
    private double paidCharges;
    @SerializedName("expectedPenalties")
    @Expose
    private double expectedPenalties;
    @SerializedName("paidPenalties")
    @Expose
    private double paidPenalties;
    @SerializedName("scheduledPayment")
    @Expose
    private double scheduledPayment;
    @SerializedName("expectedTotal")
    @Expose
    private double expectedTotal;
    @SerializedName("paidTotal")
    @Expose
    private double paidTotal;
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

    public double getExpectedInterest() {
        return expectedInterest;
    }

    public void setExpectedInterest(double expectedInterest) {
        this.expectedInterest = expectedInterest;
    }

    public double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public double getExpectedCharges() {
        return expectedCharges;
    }

    public void setExpectedCharges(double expectedCharges) {
        this.expectedCharges = expectedCharges;
    }

    public double getPaidCharges() {
        return paidCharges;
    }

    public void setPaidCharges(double paidCharges) {
        this.paidCharges = paidCharges;
    }

    public double getExpectedPenalties() {
        return expectedPenalties;
    }

    public void setExpectedPenalties(double expectedPenalties) {
        this.expectedPenalties = expectedPenalties;
    }

    public double getPaidPenalties() {
        return paidPenalties;
    }

    public void setPaidPenalties(double paidPenalties) {
        this.paidPenalties = paidPenalties;
    }

    public double getScheduledPayment() {
        return scheduledPayment;
    }

    public void setScheduledPayment(double scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public double getExpectedTotal() {
        return expectedTotal;
    }

    public void setExpectedTotal(double expectedTotal) {
        this.expectedTotal = expectedTotal;
    }

    public double getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(double paidTotal) {
        this.paidTotal = paidTotal;
    }
}
