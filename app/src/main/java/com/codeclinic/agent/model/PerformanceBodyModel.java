package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PerformanceBodyModel {
    @SerializedName("staffId")
    @Expose
    private String staffId;
    @SerializedName("staff")
    @Expose
    private String staff;
    @SerializedName("overallPerformance")
    @Expose
    private Integer overallPerformance;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("performanceAttributes")
    @Expose
    private List<PerformanceAttributesListModel> performanceAttributesList = null;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public Integer getOverallPerformance() {
        return overallPerformance;
    }

    public void setOverallPerformance(Integer overallPerformance) {
        this.overallPerformance = overallPerformance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<PerformanceAttributesListModel> getPerformanceAttributesList() {
        return performanceAttributesList;
    }

    public void setPerformanceAttributesList(List<PerformanceAttributesListModel> performanceAttributesList) {
        this.performanceAttributesList = performanceAttributesList;
    }
}
