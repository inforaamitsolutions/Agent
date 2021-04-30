package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanStatusModel {
    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("body")
    @Expose
    private List<String> loanStatuses = null;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("httpStatus")
    @Expose
    private String httpStatus;

    public String getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(String successStatus) {
        this.successStatus = successStatus;
    }

    public List<String> getLoanStatuses() {
        return loanStatuses;
    }

    public void setLoanStatuses(List<String> loanStatuses) {
        this.loanStatuses = loanStatuses;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }


}
