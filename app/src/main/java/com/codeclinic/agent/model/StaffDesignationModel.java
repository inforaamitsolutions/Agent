package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffDesignationModel {
    @SerializedName("designationName")
    @Expose
    private String designationName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("stopDate")
    @Expose
    private Object stopDate;

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getStopDate() {
        return stopDate;
    }

    public void setStopDate(Object stopDate) {
        this.stopDate = stopDate;
    }
}
