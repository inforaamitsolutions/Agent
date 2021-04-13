package com.codeclinic.agent.model.leadInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeadInteractionHistoryModel {

    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<LeadInteractionHistoryListModel> body = null;
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

    public List<LeadInteractionHistoryListModel> getBody() {
        return body;
    }

    public void setBody(List<LeadInteractionHistoryListModel> body) {
        this.body = body;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
