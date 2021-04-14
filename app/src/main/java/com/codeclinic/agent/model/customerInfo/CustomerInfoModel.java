package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerInfoModel {
    @SerializedName("submissionId")
    @Expose
    private Integer submissionId;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("submissionDate")
    @Expose
    private String submissionDate;
    @SerializedName("pages")
    @Expose
    private List<CustomerInfoPagesModel> customerInfoPages = null;

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<CustomerInfoPagesModel> getCustomerInfoPages() {
        return customerInfoPages;
    }

    public void setCustomerInfoPages(List<CustomerInfoPagesModel> customerInfoPages) {
        this.customerInfoPages = customerInfoPages;
    }
}
