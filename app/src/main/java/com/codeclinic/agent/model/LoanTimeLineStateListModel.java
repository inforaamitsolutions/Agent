package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanTimeLineStateListModel {
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
    @SerializedName("loanStatus")
    @Expose
    private String loanStatus;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("stopDateTime")
    @Expose
    private String stopDateTime;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("stateStatus")
    @Expose
    private String stateStatus;
    @SerializedName("timelineStateStatus")
    @Expose
    private String timelineStateStatus;

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

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStopDateTime() {
        return stopDateTime;
    }

    public void setStopDateTime(String stopDateTime) {
        this.stopDateTime = stopDateTime;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
    }

    public String getTimelineStateStatus() {
        return timelineStateStatus;
    }

    public void setTimelineStateStatus(String timelineStateStatus) {
        this.timelineStateStatus = timelineStateStatus;
    }
}
