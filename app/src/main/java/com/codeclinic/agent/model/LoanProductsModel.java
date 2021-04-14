package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanProductsModel {
    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<LoanProductListModel> loanProductList = null;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LoanProductListModel> getLoanProductList() {
        return loanProductList;
    }

    public void setLoanProductList(List<LoanProductListModel> loanProductList) {
        this.loanProductList = loanProductList;
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
