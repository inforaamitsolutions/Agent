package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentLoanStatusModel {
    @SerializedName("loanStatusId")
    @Expose
    private Integer loanStatusId;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("stopDateTime")
    @Expose
    private Object stopDateTime;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Object getStopDateTime() {
        return stopDateTime;
    }

    public void setStopDateTime(Object stopDateTime) {
        this.stopDateTime = stopDateTime;
    }
}
