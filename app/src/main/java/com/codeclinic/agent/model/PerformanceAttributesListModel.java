package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceAttributesListModel {
    @SerializedName("attibuteName")
    @Expose
    private String attibuteName;
    @SerializedName("attibuteLabel")
    @Expose
    private String attibuteLabel;
    @SerializedName("performance")
    @Expose
    private Integer performance;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("actual")
    @Expose
    private String actual;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    public String getAttibuteName() {
        return attibuteName;
    }

    public void setAttibuteName(String attibuteName) {
        this.attibuteName = attibuteName;
    }

    public String getAttibuteLabel() {
        return attibuteLabel;
    }

    public void setAttibuteLabel(String attibuteLabel) {
        this.attibuteLabel = attibuteLabel;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
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

}
