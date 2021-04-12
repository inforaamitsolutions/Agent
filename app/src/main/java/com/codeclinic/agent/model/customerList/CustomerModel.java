package com.codeclinic.agent.model.customerList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerModel {
    @SerializedName("successStatus")
    @Expose
    private String successStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<CustomerListModel> customerList = null;
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

    public List<CustomerListModel> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerListModel> customerList) {
        this.customerList = customerList;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
