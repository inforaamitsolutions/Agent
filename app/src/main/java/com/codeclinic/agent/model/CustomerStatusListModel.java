package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerStatusListModel {
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("loanStatusId")
    @Expose
    private Integer loanStatusId;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("stopDateTime")
    @Expose
    private String stopDateTime;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getLoanStatusId() {
        return loanStatusId;
    }

    public void setLoanStatusId(Integer loanStatusId) {
        this.loanStatusId = loanStatusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
}
