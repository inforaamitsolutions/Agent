package com.codeclinic.agent.model.leadInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadInfoModel {
    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private LeadInfoDetailModel leadInfo;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    public String getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(String successStatus) {
        this.successStatus = successStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LeadInfoDetailModel getLeadInfo() {
        return leadInfo;
    }

    public void setLeadInfo(LeadInfoDetailModel leadInfo) {
        this.leadInfo = leadInfo;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
