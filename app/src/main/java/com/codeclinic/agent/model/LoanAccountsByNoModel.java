package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanAccountsByNoModel {
    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("body")
    @Expose
    private LoanAccountsByNoDetailModel loanAccountsByNoDetail;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
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

    public LoanAccountsByNoDetailModel getLoanAccountsByNoDetail() {
        return loanAccountsByNoDetail;
    }

    public void setLoanAccountsByNoDetail(LoanAccountsByNoDetailModel loanAccountsByNoDetail) {
        this.loanAccountsByNoDetail = loanAccountsByNoDetail;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
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
