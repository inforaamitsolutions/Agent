package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanAccountListModel {

    @SerializedName("loanNumber")
    @Expose
    private String loanNumber;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("subscriptionId")
    @Expose
    private Integer subscriptionId;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("loanAccountId")
    @Expose
    private Integer loanAccountId;
    @SerializedName("loanAmount")
    @Expose
    private Integer loanAmount;
    @SerializedName("applicationId")
    @Expose
    private Integer applicationId;
    @SerializedName("applicationStatus")
    @Expose
    private String applicationStatus;
    @SerializedName("loanInterest")
    @Expose
    private Integer loanInterest;
    @SerializedName("loanCharges")
    @Expose
    private Integer loanCharges;
    @SerializedName("loanPenalty")
    @Expose
    private Integer loanPenalty;
    @SerializedName("runningBalance")
    @Expose
    private Integer runningBalance;
    @SerializedName("commencementDate")
    @Expose
    private String commencementDate;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;
    @SerializedName("noOfInstallments")
    @Expose
    private Integer noOfInstallments;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("rolloverCount")
    @Expose
    private Integer rolloverCount;
    @SerializedName("partnerId")
    @Expose
    private String partnerId;
    @SerializedName("destinationAccount")
    @Expose
    private String destinationAccount;
    @SerializedName("customerPhoneNumber")
    @Expose
    private String customerPhoneNumber;

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getLoanAccountId() {
        return loanAccountId;
    }

    public void setLoanAccountId(Integer loanAccountId) {
        this.loanAccountId = loanAccountId;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(Integer loanInterest) {
        this.loanInterest = loanInterest;
    }

    public Integer getLoanCharges() {
        return loanCharges;
    }

    public void setLoanCharges(Integer loanCharges) {
        this.loanCharges = loanCharges;
    }

    public Integer getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(Integer loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    public Integer getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(Integer runningBalance) {
        this.runningBalance = runningBalance;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Integer getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(Integer noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRolloverCount() {
        return rolloverCount;
    }

    public void setRolloverCount(Integer rolloverCount) {
        this.rolloverCount = rolloverCount;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
