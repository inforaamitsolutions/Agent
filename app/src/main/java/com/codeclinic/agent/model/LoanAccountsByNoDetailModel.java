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
    private List<InstallmentListModel> installment = null;
    @SerializedName("accountEntries")
    @Expose
    private List<LoanAccountEntryListModel> accountEntries = null;
    @SerializedName("currentStatus")
    @Expose
    private CurrentLoanStatusModel currentStatus;
    @SerializedName("statuses")
    @Expose
    private List<CustomerStatusListModel> statuses = null;
    @SerializedName("currentTimelineState")
    @Expose
    private Object currentTimelineState;
    @SerializedName("timelineState")
    @Expose
    private List<LoanTimeLineStateListModel> timelineState = null;


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

    public List<InstallmentListModel> getInstallment() {
        return installment;
    }

    public void setInstallment(List<InstallmentListModel> installment) {
        this.installment = installment;
    }

    public List<LoanAccountEntryListModel> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<LoanAccountEntryListModel> accountEntries) {
        this.accountEntries = accountEntries;
    }

    public CurrentLoanStatusModel getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CurrentLoanStatusModel currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<CustomerStatusListModel> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CustomerStatusListModel> statuses) {
        this.statuses = statuses;
    }

    public Object getCurrentTimelineState() {
        return currentTimelineState;
    }

    public void setCurrentTimelineState(Object currentTimelineState) {
        this.currentTimelineState = currentTimelineState;
    }

    public List<LoanTimeLineStateListModel> getTimelineState() {
        return timelineState;
    }

    public void setTimelineState(List<LoanTimeLineStateListModel> timelineState) {
        this.timelineState = timelineState;
    }
}
