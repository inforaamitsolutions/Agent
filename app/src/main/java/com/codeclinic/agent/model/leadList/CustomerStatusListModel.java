package com.codeclinic.agent.model.leadList;

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
}
