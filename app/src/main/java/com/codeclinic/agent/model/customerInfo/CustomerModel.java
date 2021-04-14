package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerModel {
    @SerializedName("body")
    @Expose
    private CustomerInfoModel customerInfo;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("statusResponse")
    @Expose
    private String statusResponse;

    public CustomerInfoModel getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoModel customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

}
