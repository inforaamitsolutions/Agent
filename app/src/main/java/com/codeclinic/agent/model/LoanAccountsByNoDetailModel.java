package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanAccountsByNoDetailModel {
    @SerializedName("account")
    @Expose
    private LoanAccountListModel account;
    @SerializedName("partnerPhoneNumber")
    @Expose
    private String partnerPhoneNumber;
    @SerializedName("installment")
    @Expose
    private List<Object> installment = null;
    @SerializedName("accountEntries")
    @Expose
    private List<Object> accountEntries = null;
    @SerializedName("currentStatus")
    @Expose
    private Object currentStatus;
    @SerializedName("statuses")
    @Expose
    private List<Object> statuses = null;
    @SerializedName("currentTimelineState")
    @Expose
    private Object currentTimelineState;
    @SerializedName("timelineState")
    @Expose
    private List<Object> timelineState = null;

    public LoanAccountListModel getAccount() {
        return account;
    }

    public void setAccount(LoanAccountListModel account) {
        this.account = account;
    }

    public String getPartnerPhoneNumber() {
        return partnerPhoneNumber;
    }

    public void setPartnerPhoneNumber(String partnerPhoneNumber) {
        this.partnerPhoneNumber = partnerPhoneNumber;
    }

    public List<Object> getInstallment() {
        return installment;
    }

    public void setInstallment(List<Object> installment) {
        this.installment = installment;
    }

    public List<Object> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<Object> accountEntries) {
        this.accountEntries = accountEntries;
    }

    public Object getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Object currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<Object> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Object> statuses) {
        this.statuses = statuses;
    }

    public Object getCurrentTimelineState() {
        return currentTimelineState;
    }

    public void setCurrentTimelineState(Object currentTimelineState) {
        this.currentTimelineState = currentTimelineState;
    }

    public List<Object> getTimelineState() {
        return timelineState;
    }

    public void setTimelineState(List<Object> timelineState) {
        this.timelineState = timelineState;
    }

}
