package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfoPagesModel {
    @SerializedName("socialData")
    @Expose
    private CustomerSocialDataModel customerSocialData;
    @SerializedName("supplierData")
    @Expose
    private CustomerSupplierDataModel customerSupplierData;
    @SerializedName("businessData")
    @Expose
    private CustomerBusinessDataModel customerBusinessData;
    @SerializedName("customerBioData")
    @Expose
    private CustomerBioDataModel customerBioData;
    @SerializedName("leadDetails")
    @Expose
    private CustomerLeadDataModel customerLeadData;


    public CustomerSocialDataModel getCustomerSocialData() {
        return customerSocialData;
    }

    public void setCustomerSocialData(CustomerSocialDataModel customerSocialData) {
        this.customerSocialData = customerSocialData;
    }

    public CustomerSupplierDataModel getCustomerSupplierData() {
        return customerSupplierData;
    }

    public void setCustomerSupplierData(CustomerSupplierDataModel customerSupplierData) {
        this.customerSupplierData = customerSupplierData;
    }

    public CustomerBusinessDataModel getCustomerBusinessData() {
        return customerBusinessData;
    }

    public void setCustomerBusinessData(CustomerBusinessDataModel customerBusinessData) {
        this.customerBusinessData = customerBusinessData;
    }

    public CustomerBioDataModel getCustomerBioData() {
        return customerBioData;
    }

    public void setCustomerBioData(CustomerBioDataModel customerBioData) {
        this.customerBioData = customerBioData;
    }

    public CustomerLeadDataModel getCustomerLeadData() {
        return customerLeadData;
    }

    public void setCustomerLeadData(CustomerLeadDataModel customerLeadData) {
        this.customerLeadData = customerLeadData;
    }
}
