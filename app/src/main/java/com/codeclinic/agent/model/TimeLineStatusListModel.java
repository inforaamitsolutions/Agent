package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeLineStatusListModel {

    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("loanProductId")
    @Expose
    private TimeLineLoanProductDetailModel timeLineLoanProductDetail;
    @SerializedName("timelineState")
    @Expose
    private String timelineState;
    @SerializedName("referenceDate")
    @Expose
    private String referenceDate;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("timeUnit")
    @Expose
    private String timeUnit;
    @SerializedName("startTime")
    @Expose
    private Integer startTime;
    @SerializedName("endTime")
    @Expose
    private Integer endTime;
    @SerializedName("timelineStatus")
    @Expose
    private String timelineStatus;
    @SerializedName("description")
    @Expose
    private String description;

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public TimeLineLoanProductDetailModel getTimeLineLoanProductDetail() {
        return timeLineLoanProductDetail;
    }

    public void setTimeLineLoanProductDetail(TimeLineLoanProductDetailModel timeLineLoanProductDetail) {
        this.timeLineLoanProductDetail = timeLineLoanProductDetail;
    }

    public String getTimelineState() {
        return timelineState;
    }

    public void setTimelineState(String timelineState) {
        this.timelineState = timelineState;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getTimelineStatus() {
        return timelineStatus;
    }

    public void setTimelineStatus(String timelineStatus) {
        this.timelineStatus = timelineStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "" + timelineState;
    }
}
